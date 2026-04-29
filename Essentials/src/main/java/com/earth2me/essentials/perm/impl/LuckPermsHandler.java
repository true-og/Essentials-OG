package com.earth2me.essentials.perm.impl;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.context.ContextCalculator;
import net.luckperms.api.context.ContextConsumer;
import net.luckperms.api.context.ContextSet;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LuckPermsHandler extends SuperpermsHandler {
    private LuckPerms luckPerms;
    private Essentials ess;
    private CombinedCalculator calculator;

    @Override
    protected boolean emulateWildcards() {
        return false;
    }

    @Override
    public boolean tryProvider(Essentials ess) {
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null) {
            return false;
        }
        try {
            Class.forName("net.luckperms.api.LuckPerms");
        } catch (final ClassNotFoundException e) {
            return false;
        }
        final RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            return false;
        }
        this.luckPerms = provider.getProvider();
        this.ess = ess;
        return this.luckPerms != null;
    }

    @Override
    public String getBackendName() {
        return "LuckPerms";
    }

    @Override
    public String getEnabledPermsPlugin() {
        return "LuckPerms";
    }

    @Override
    public boolean canBuild(final Player base, final String group) {
        if (super.canBuild(base, group)) {
            return true;
        }
        final CachedMetaData meta = playerMeta(base);
        if (meta == null) {
            return false;
        }
        final String value = meta.getMetaValue("build");
        return value != null && Boolean.parseBoolean(value);
    }

    @Override
    public String getGroup(final OfflinePlayer base) {
        final net.luckperms.api.model.user.User lpUser = loadUser(base.getUniqueId());
        if (lpUser == null) {
            return null;
        }
        return lpUser.getPrimaryGroup();
    }

    @Override
    public List<String> getGroups(final OfflinePlayer base) {
        final net.luckperms.api.model.user.User lpUser = loadUser(base.getUniqueId());
        if (lpUser == null) {
            return Collections.emptyList();
        }
        final QueryOptions options = lpUser.getQueryOptions();
        return lpUser.resolveInheritedNodes(options).stream()
            .filter(NodeType.INHERITANCE::matches)
            .map(NodeType.INHERITANCE::cast)
            .map(InheritanceNode::getGroupName)
            .distinct()
            .collect(Collectors.toList());
    }

    @Override
    public List<String> getGroups() {
        final List<String> groups = new ArrayList<>();
        for (final Group group : luckPerms.getGroupManager().getLoadedGroups()) {
            groups.add(group.getName());
        }
        return groups;
    }

    @Override
    public boolean inGroup(final Player base, final String group) {
        final net.luckperms.api.model.user.User lpUser = luckPerms.getUserManager().getUser(base.getUniqueId());
        if (lpUser == null) {
            return super.inGroup(base, group);
        }
        return lpUser.resolveInheritedNodes(lpUser.getQueryOptions()).stream()
            .filter(NodeType.INHERITANCE::matches)
            .map(NodeType.INHERITANCE::cast)
            .anyMatch(node -> node.getGroupName().equalsIgnoreCase(group));
    }

    @Override
    public boolean addToGroup(OfflinePlayer base, String group) {
        return modifyGroup(base.getUniqueId(), group, true);
    }

    @Override
    public boolean removeFromGroup(OfflinePlayer base, String group) {
        return modifyGroup(base.getUniqueId(), group, false);
    }

    private boolean modifyGroup(final UUID uuid, final String group, final boolean add) {
        final Group lpGroup = luckPerms.getGroupManager().getGroup(group);
        if (lpGroup == null) {
            return false;
        }
        final UserManager userManager = luckPerms.getUserManager();
        try {
            return userManager.modifyUser(uuid, user -> {
                final InheritanceNode node = InheritanceNode.builder(lpGroup).build();
                if (add) {
                    user.data().add(node);
                } else {
                    user.data().remove(node);
                }
            }).thenApply(v -> true).get();
        } catch (final InterruptedException | ExecutionException e) {
            return false;
        }
    }

    @Override
    public String getPrefix(final Player base) {
        final CachedMetaData meta = playerMeta(base);
        return meta == null ? null : meta.getPrefix();
    }

    @Override
    public String getSuffix(final Player base) {
        final CachedMetaData meta = playerMeta(base);
        return meta == null ? null : meta.getSuffix();
    }

    private CachedMetaData playerMeta(final Player base) {
        final net.luckperms.api.model.user.User lpUser = luckPerms.getUserManager().getUser(base.getUniqueId());
        if (lpUser == null) {
            return null;
        }
        return lpUser.getCachedData().getMetaData(QueryOptions.contextual(luckPerms.getContextManager().getContext(base)));
    }

    private net.luckperms.api.model.user.User loadUser(final UUID uuid) {
        final UserManager userManager = luckPerms.getUserManager();
        net.luckperms.api.model.user.User lpUser = userManager.getUser(uuid);
        if (lpUser != null) {
            return lpUser;
        }
        try {
            final CompletableFuture<net.luckperms.api.model.user.User> future = userManager.loadUser(uuid);
            return future.get();
        } catch (final InterruptedException | ExecutionException e) {
            return null;
        }
    }

    @Override
    public void registerContext(final String context, final Function<User, Iterable<String>> calculator, final Supplier<Iterable<String>> suggestions) {
        if (this.calculator == null) {
            this.calculator = new CombinedCalculator();
            this.luckPerms.getContextManager().registerCalculator(this.calculator);
        }
        this.calculator.calculators.add(new Calculator(context, calculator, suggestions));
    }

    @Override
    public void unregisterContexts() {
        if (this.calculator != null) {
            this.luckPerms.getContextManager().unregisterCalculator(this.calculator);
            this.calculator = null;
        }
    }

    private static final class Calculator {
        private final String id;
        private final Function<User, Iterable<String>> function;
        private final Supplier<Iterable<String>> suggestions;

        private Calculator(String id, Function<User, Iterable<String>> function, Supplier<Iterable<String>> suggestions) {
            this.id = id;
            this.function = function;
            this.suggestions = suggestions;
        }
    }

    private class CombinedCalculator implements ContextCalculator<Player> {
        private final Set<Calculator> calculators = new HashSet<>();

        @Override
        public void calculate(final Player target, final ContextConsumer consumer) {
            if (!ess.getUsers().getAllUserUUIDs().contains(target.getUniqueId())) {
                return;
            }

            final User user = ess.getUsers().loadUncachedUser(target.getUniqueId());
            if (user == null) {
                return;
            }

            for (Calculator calculator : this.calculators) {
                calculator.function.apply(user).forEach(value -> consumer.accept(calculator.id, value));
            }
        }

        @Override
        public ContextSet estimatePotentialContexts() {
            final ImmutableContextSet.Builder builder = ImmutableContextSet.builder();
            for (Calculator calculator : this.calculators) {
                calculator.suggestions.get().forEach(value -> builder.add(calculator.id, value));
            }
            return builder.build();
        }
    }
}
