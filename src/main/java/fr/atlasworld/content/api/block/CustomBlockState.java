package fr.atlasworld.content.api.block;

import org.bukkit.Instrument;

public class CustomBlockState {
    private int instrument;
    private int note;
    private boolean powered;

    public static final Instrument[] instruments = new Instrument[] {Instrument.PIANO, Instrument.BASS_DRUM, Instrument.SNARE_DRUM, Instrument.STICKS,
            Instrument.BASS_GUITAR, Instrument.FLUTE, Instrument.BELL, Instrument.GUITAR, Instrument.CHIME, Instrument.XYLOPHONE,
            Instrument.IRON_XYLOPHONE, Instrument.COW_BELL, Instrument.DIDGERIDOO, Instrument.BIT, Instrument.BANJO, Instrument.PLING};

    public CustomBlockState(int instrument, int note, boolean powered) {
        this.instrument = instrument;
        this.note = note;
        this.powered = powered;
    }

    public int getInstrument() {
        return instrument;
    }

    public int getNote() {
        return note;
    }

    public boolean isPowered() {
        return powered;
    }

    public CustomBlockState getPos() {
        return new CustomBlockState(this.instrument, this.note, this.powered);
    }

    public void increase() {
        int noteMaxSize = 24;

        this.note++;
        if (this.note > noteMaxSize) {
            this.note = 0;
            this.instrument++;
            if (this.instrument > instruments.length) {
                this.instrument++;
                if (this.powered) {
                    throw new IllegalStateException("Max blocks are registered! Unable to register more blocks!");
                }
                this.powered = true;
            }
        }
    }

    public static Instrument getInstrument(int i) {
        return instruments[i];
    }
}
