package com.mastermarisa.solmaiddream.api.event;

import net.neoforged.bus.api.Event;

public class DayChangedEvent extends Event {
    private final long newDay;

    public DayChangedEvent(long newDay){
        this.newDay = newDay;
    }

    public long getNewDay() { return newDay; }
}
