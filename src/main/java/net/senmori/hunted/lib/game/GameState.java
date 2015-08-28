package net.senmori.hunted.lib.game;

/**
 * Stores the state of players <br>
 * {@link #LOBBY} - if the player is in the Hunted lobby area <br>
 * {@link #IN_GAME} - if the player is in the Hunted arena <br>
 * {@link #IN_STORE} - if the player is in the Hunted store <br>
 * {@link NOT_PLAYING} - if the player is not playing, but still online
 * {@link #OFFLINE} - if the player is offline but still stored for whatever reason <br>
 */
public enum GameState {

    LOBBY,
    IN_GAME,
    IN_STORE,
    NOT_PLAYING,
    OFFLINE;
}
