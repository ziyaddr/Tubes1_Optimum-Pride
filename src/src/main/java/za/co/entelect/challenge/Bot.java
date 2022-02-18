package za.co.entelect.challenge;

import za.co.entelect.challenge.command.*;
import za.co.entelect.challenge.entities.*;
import za.co.entelect.challenge.enums.PowerUps;
import za.co.entelect.challenge.enums.State;
import za.co.entelect.challenge.enums.Terrain;

import java.util.*;

import static java.lang.Math.max;

public class Bot {

    private static final int maxSpeed = 9;
    private List<Integer> directionList = new ArrayList<>();

    private Random random;
    private GameState gameState;
    private Car opponent;
    private Car myCar;
    private final static Command FIX = new FixCommand();
    private final static Command ACCELERATE = new AccelerateCommand();
    private final static Command LIZARD = new LizardCommand();
    private final static Command OIL = new OilCommand();
    private final static Command BOOST = new BoostCommand();
    private final static Command EMP = new EmpCommand();

    public Bot(Random random, GameState gameState) {
        this.random = random;
        this.gameState = gameState;
        this.myCar = gameState.player;
        this.opponent = gameState.opponent;

        directionList.add(-1);
        directionList.add(1);
    }
    private int getAcceleratedSpeed()
    {
        if(myCar.speed == 0)
        {
            return 3;
        }
        if(myCar.speed == 3)
        {
            return 6;
        }
        if(myCar.speed == 6)
        {
            return 8;
        }
        if(myCar.speed == 8)
        {
            return 9;
        }
        return myCar.speed;
    }

    public Command run() {
        int acceleratedSpeed = getAcceleratedSpeed();
        List<Object> blocks = getBlocksInFront(myCar.position.lane, myCar.position.block, myCar.speed-1);
        List<Object> acceleratedBlocks = getBlocksInFront(myCar.position.lane, myCar.position.block, acceleratedSpeed);
        List<Object> boostBlocks = getBlocksInFront(myCar.position.lane, myCar.position.block, 15);

        //fix when speed can't reach >= 8
        if(myCar.damage >= 3) {
            return FIX;
        }
        //accelerate when speed is 0
        if(myCar.speed<=0)
        {
            return ACCELERATE;
        }
        //incer boost lizard
        if(myCar.position.lane > 1)
        {
            List<Object> leftBlocks = getBlocksInFront(myCar.position.lane-1, myCar.position.block, myCar.speed-1);
            if(!leftBlocks.contains(Terrain.MUD) && !leftBlocks.contains(Terrain.WALL)&& !leftBlocks.contains(Terrain.OIL_SPILL)&&(leftBlocks.contains(Terrain.BOOST)||leftBlocks.contains(Terrain.LIZARD)))
            {
                return new ChangeLaneCommand(-1);
            }
        }
        if(myCar.position.lane < 4)
        {
            List<Object> rightBlocks = getBlocksInFront(myCar.position.lane+1, myCar.position.block, myCar.speed-1);
            if(!rightBlocks.contains(Terrain.MUD)&&!rightBlocks.contains(Terrain.WALL)&&!rightBlocks.contains(Terrain.OIL_SPILL)&&(rightBlocks.contains(Terrain.BOOST)||rightBlocks.contains(Terrain.LIZARD)))
            {
                return new ChangeLaneCommand(1);
            }
        }
        //incer tweet emp
        if(myCar.position.lane > 1)
        {
            List<Object> leftBlocks = getBlocksInFront(myCar.position.lane-1, myCar.position.block, myCar.speed-1);
            if(!leftBlocks.contains(Terrain.MUD) && !leftBlocks.contains(Terrain.WALL)&& !leftBlocks.contains(Terrain.OIL_SPILL)&&(leftBlocks.contains(Terrain.EMP)||leftBlocks.contains(Terrain.TWEET)))
            {
                return new ChangeLaneCommand(-1);
            }
        }
        if(myCar.position.lane < 4)
        {
            List<Object> rightBlocks = getBlocksInFront(myCar.position.lane+1, myCar.position.block,  myCar.speed-1);
            if(!rightBlocks.contains(Terrain.MUD)&&!rightBlocks.contains(Terrain.WALL)&&!rightBlocks.contains(Terrain.OIL_SPILL)&&(rightBlocks.contains(Terrain.EMP)||rightBlocks.contains(Terrain.TWEET)))
            {
                return new ChangeLaneCommand(1);
            }
        }
        //check for obstacle
        if(blocks.contains(Terrain.MUD) || blocks.contains(Terrain.WALL) || blocks.contains(Terrain.OIL_SPILL))
        {
            //find empty lane
            if(myCar.position.lane > 1)
            {
                List<Object> leftBlocks = getBlocksInFront(myCar.position.lane-1, myCar.position.block, myCar.speed-1);
                if(!leftBlocks.contains(Terrain.MUD) && !leftBlocks.contains(Terrain.WALL)&& !leftBlocks.contains(Terrain.OIL_SPILL))
                {
                    return new ChangeLaneCommand(-1);
                }
            }
            if(myCar.position.lane < 4)
            {
                List<Object> rightBlocks = getBlocksInFront(myCar.position.lane+1, myCar.position.block, myCar.speed-1);
                if(!rightBlocks.contains(Terrain.MUD)&&!rightBlocks.contains(Terrain.WALL)&&!rightBlocks.contains(Terrain.OIL_SPILL))
                {
                    return new ChangeLaneCommand(1);
                }
            }
            //if none, jump
            if(hasPowerUp(PowerUps.LIZARD, myCar.powerups))
            {
                return LIZARD;
            }
            //if none, find lane without wall
            if(myCar.position.lane > 1)
            {
                List<Object> leftBlocks = getBlocksInFront(myCar.position.lane-1, myCar.position.block, myCar.speed-1);
                if(!leftBlocks.contains(Terrain.WALL)&&!(blocks.contains(Terrain.BOOST)&&!blocks.contains(Terrain.LIZARD)&&!blocks.contains(Terrain.WALL)))
                {
                    return new ChangeLaneCommand(-1);
                }
            }
            if(myCar.position.lane < 4)
            {
                List<Object> rightBlocks = getBlocksInFront(myCar.position.lane+1, myCar.position.block, myCar.speed-1);
                if(!rightBlocks.contains(Terrain.WALL)&&!(blocks.contains(Terrain.BOOST)&&!blocks.contains(Terrain.LIZARD)&&!blocks.contains(Terrain.WALL)))
                {
                    return new ChangeLaneCommand(1);
                }
            }
        }
        //check if opponent is in front
        if (opponent.position.lane == myCar.position.lane && opponent.position.block >= myCar.position.block+myCar.speed) 
        {
            //check if left or right is empty
            if(myCar.position.lane > 1)
            {
                List<Object> leftBlocks = getBlocksInFront(myCar.position.lane-1, myCar.position.block, myCar.speed-1);
                if(!leftBlocks.contains(Terrain.MUD) && !leftBlocks.contains(Terrain.WALL)&& !leftBlocks.contains(Terrain.OIL_SPILL))
                {
                    return new ChangeLaneCommand(-1);
                }
            }
            if(myCar.position.lane < 4)
            {
                List<Object> rightBlocks = getBlocksInFront(myCar.position.lane+1, myCar.position.block, myCar.speed-1);
                if(!rightBlocks.contains(Terrain.MUD)&&!rightBlocks.contains(Terrain.WALL)&&!rightBlocks.contains(Terrain.OIL_SPILL))
                {
                    return new ChangeLaneCommand(1);
                }
            }
            //jump if possible
            if(hasPowerUp(PowerUps.LIZARD, myCar.powerups)&&myCar.speed>opponent.speed)
            {
                return LIZARD;
            }
            //choose either
            if(myCar.position.lane > 1)
            {
                return new ChangeLaneCommand(-1);
            }
            if(myCar.position.lane < 4)
            {
                return new ChangeLaneCommand(1);
            }
        }
        //if have boost
        if(hasPowerUp(PowerUps.BOOST, myCar.powerups)&&myCar.speed<15 && !boostBlocks.contains(Terrain.WALL) && !boostBlocks.contains(Terrain.MUD))
        {
            //fix first then boost next round
            if(myCar.damage>0)
            {
                return FIX;
            }
            return BOOST;
        }
        //if not max speed yet
        if(myCar.speed<maxSpeed && !acceleratedBlocks.contains(Terrain.WALL) && !acceleratedBlocks.contains(Terrain.MUD) && !acceleratedBlocks.contains(Terrain.OIL_SPILL))
        {
            return ACCELERATE;
        }
        //use emp
        if(hasPowerUp(PowerUps.EMP, myCar.powerups)&&opponent.position.block>myCar.position.block&&(opponent.position.lane==myCar.position.lane||opponent.position.lane==myCar.position.lane+1||opponent.position.lane==myCar.position.lane-1))
        {
            return EMP;
        }
        //use tweet
        if(hasPowerUp(PowerUps.TWEET, myCar.powerups))
        {
            return new TweetCommand(opponent.position.lane, opponent.position.block+3+opponent.speed);
        }
        //use oil
        if(hasPowerUp(PowerUps.OIL, myCar.powerups)&&opponent.position.block<myCar.position.block)
        {
            return OIL;
        }
        return new DoNothingCommand();
    }
    private Boolean hasPowerUp(PowerUps powerUpToCheck, PowerUps[] available) {
        for (PowerUps powerUp: available) {
            if (powerUp.equals(powerUpToCheck)) 
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns map of blocks and the objects in the for the current lanes, returns the amount of blocks that can be
     * traversed at max speed.
     **/
    private List<Object> getBlocksInFront(int lane, int block, int lookAhead) {
        List<Lane[]> map = gameState.lanes;
        List<Object> blocks = new ArrayList<>();
        int startBlock = map.get(0)[0].position.block;

        Lane[] laneList = map.get(lane - 1);
        for (int i = max(block - startBlock, 0); i <= block - startBlock + lookAhead; i++) {
            if (laneList[i] == null || laneList[i].terrain == Terrain.FINISH) {
                break;
            }

            blocks.add(laneList[i].terrain);

        }
        return blocks;
    }

}
