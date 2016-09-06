/*
 * This file is part of SpongeSurvivalGamesPlugin, licensed under the MIT License (MIT).
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
package io.github.m0pt0pmatt.spongesurvivalgames.tasks;

import com.flowpowered.math.vector.Vector3i;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.util.TextMessageException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.github.m0pt0pmatt.spongesurvivalgames.game.SurvivalGame;

public class SpawnPlayersTask implements Task {

    private static final Task INSTANCE = new CheckWinTask();

    @Override
    public void execute(SurvivalGame survivalGame) throws TextMessageException {

        List<Vector3i> spawns = new ArrayList<>(survivalGame.getConfig().getSpawns());

        survivalGame.getConfig().getWorldName().ifPresent(worldName ->
                Sponge.getServer().getWorld(worldName).ifPresent(world ->
                        survivalGame.getPlayerUUIDs().forEach(playerId ->
                                Sponge.getServer().getPlayer(playerId).ifPresent(player -> {
                                    if (spawns.isEmpty()) {
                                        return;
                                    }
                                    player.setLocation(world.getLocation(spawns.remove(0)));
                                }))));
    }

    public static Task getInstance() {
        return INSTANCE;
    }
}
