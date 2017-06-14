package com.threepillar.meetingroomfinder.network;

import com.threepillar.meetingroomfinder.models.Room;

/**
 * Created by nikhil.mehta on 6/12/2017.
 */

public class JSONResponse {

    private Room[] rooms;

    public Room[] getRooms(){
        return rooms;
    }

}
