package es.ulpgc.bowling;

import java.util.ArrayList;
import java.util.List;

public class PlayerScore {
    private final List<Integer> rolls;

    public PlayerScore(String name) {
        this.rolls = new ArrayList<>();
    }

    public List<Frame> frames() {
        List<Frame> frames = new ArrayList<>();
        for (int i = 0; i < rolls.size() && frames.size() < 10;  i += getNextIteration(frames)) {
            frames.add(new Frame(i));
        }

        return frames;
    }

    private int getNextIteration(List<Frame> frames) {
        return frames.get(frames.size()-1).isStrike() ? 1: 2;
    }

    public PlayerScore roll(int pins) {
        rolls.add(pins);
        return this;
    }

    public class Frame {
        private int offset;

        public Frame(int offset) {
            this.offset = offset;
        }

        public Integer score() {
            return isComplete() ? sumOfRolls() : null;
        }

        private int sumOfRolls() {
            return roll(0) + roll(1) + (isStrike() || isSpare() ? roll(2) : 0);
        }

        private boolean isComplete() {
            return rollsDone() >= rollsToComplete();
        }

        private int rollsDone() {
            return rolls.size() - offset;
        }

        private int rollsToComplete() {
            return isStrike() || isSpare() ? 3 : 2;
        }

        private boolean isSpare() {
            return roll(0) + roll(1) == 10;
        }

        public boolean isStrike() {
            return roll(0) == 10;
        }

        private Integer roll(int i) {
            return isOutOfBounds(i) ? rolls.get(i + offset) : 0;
        }

        private boolean isOutOfBounds(int i) {
            return i < rolls.size() - offset;
        }
    }
}