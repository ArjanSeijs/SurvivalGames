/*
 * This file is part of SurvivalGames, licensed under the MIT License (MIT).
 *
 * Copyright (c) Matthew Broomfield <m0pt0pmatt17@gmail.com>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.m0pt0pmatt.survivalgames.command.executor;

import static io.github.m0pt0pmatt.survivalgames.Util.getOrThrow;

import io.github.m0pt0pmatt.survivalgames.command.CommandKeys;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nonnull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

class TeleportCommand extends LeafCommand {

    private static final TeleportCommand INSTANCE = new TeleportCommand();

    private TeleportCommand() {
        super(RootCommand.getInstance(), "teleport", GenericArguments.world(CommandKeys.WORLD));
    }

    @Override
    @Nonnull
    public CommandResult executeCommand(@Nonnull CommandSource src, @Nonnull CommandContext args)
            throws CommandException {
        Object worldInfo = getOrThrow(args, CommandKeys.WORLD);

        String worldName;
        try {
            worldName = (String) worldInfo.getClass().getMethod("getWorldName").invoke(worldInfo);
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            throw new CommandException(Text.of("Error: " + e.getMessage()), e);
        }

        if (src instanceof Player) {
            World world = getOrThrow(Sponge.getServer().getWorld(worldName), CommandKeys.WORLD);
            ((Player) src).setLocation(world.getSpawnLocation());
        }
        return CommandResult.success();
    }

    static TeleportCommand getInstance() {
        return INSTANCE;
    }
}
