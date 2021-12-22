package io.sergeyev.battlesnake.gameengine;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    private final Random random = new Random();

    public TurnAction move(TurnData turnData) {
        final Coordinate head = turnData.you().head();
        LinkedList<MoveDirection> possibleDirections = getPossibleMoveDirections(turnData, head);

        return new TurnAction(chooseFastestRouteToNearestFood(possibleDirections, turnData), "Yo-hoo!");
    }

    private LinkedList<MoveDirection> getPossibleMoveDirections(TurnData turnData, Coordinate head) {
        var possibleDirections = new LinkedList<MoveDirection>();

        if(validate(new Coordinate(head.x(), head.y() + 1), turnData)) {
            possibleDirections.add(MoveDirection.up);
        }

        if(validate(new Coordinate(head.x() + 1, head.y()), turnData)) {
            possibleDirections.add(MoveDirection.right);
        }

        if(validate(new Coordinate(head.x(), head.y() - 1), turnData)) {
            possibleDirections.add(MoveDirection.down);
        }

        if(validate(new Coordinate(head.x() - 1, head.y()), turnData)) {
            possibleDirections.add(MoveDirection.left);
        }
        return possibleDirections;
    }

    private MoveDirection chooseFastestRouteToNearestFood(LinkedList<MoveDirection> possibleDirections, TurnData turnData) {
        final var stepsNeededToNearestFoodAndStepDirection = calculateStepsNeededToNearestFoodAndStepDirection(possibleDirections, turnData);

        final Integer stepsNeededToNearestFood = stepsNeededToNearestFoodAndStepDirection.getKey();

        boolean isLateEater = false;

        if(noFoodOnBoard(turnData) || (isLateEater && notHungry(turnData, stepsNeededToNearestFood))) {
            return getMoveDirectionThatHasPossibleNextSteps(possibleDirections, turnData, false);
        }

        return stepsNeededToNearestFoodAndStepDirection
                .getValue();
    }

    private boolean noFoodOnBoard(TurnData turnData) {
        return turnData.board().food().length == 0;
    }

    private boolean notHungry(TurnData turnData, Integer stepsNeededToNearestFood) {
        return turnData.you().health() > (stepsNeededToNearestFood * 2);
    }

    private MoveDirection getMoveDirectionThatHasPossibleNextSteps(LinkedList<MoveDirection> possibleDirections, TurnData turnData, boolean isHungry) {
        MoveDirection moveDirection;
        Coordinate moveDirectionCoordinate;
        int attempts = 0;
        do {
            attempts++;
            var nextDirection = random.nextInt(possibleDirections.size());
            moveDirection = possibleDirections.get(nextDirection);
            moveDirectionCoordinate = Coordinate.createNextPosition(turnData.you().head(), moveDirection);
        } while((destinationIsTerminal(turnData, moveDirectionCoordinate) || canAvoidFoodIfNotHungry(turnData, isHungry, moveDirectionCoordinate)) && attempts < 4);
        return moveDirection;
    }

    private boolean canAvoidFoodIfNotHungry(TurnData turnData, boolean isHungry, Coordinate moveDirectionCoordinate) {
        return !isHungry && coordinateContainsFood(turnData, moveDirectionCoordinate);
    }

    private boolean destinationIsTerminal(TurnData turnData, Coordinate moveDirectionCoordinate) {
        return getPossibleMoveDirections(turnData, moveDirectionCoordinate).isEmpty();
    }

    private boolean coordinateContainsFood(TurnData turnData, Coordinate coordinate) {
        for (Coordinate foodCoordinate : turnData.board().food()) {
            if(foodCoordinate.equals(coordinate)) return true;
        }
        return false;
    }

    private AbstractMap.SimpleImmutableEntry<Integer, MoveDirection> calculateStepsNeededToNearestFoodAndStepDirection(LinkedList<MoveDirection> possibleDirections, TurnData turnData) {
        return possibleDirections.stream()
                .map(possibleDirection ->
                        new AbstractMap.SimpleImmutableEntry<>(Coordinate.createNextPosition(turnData.you().head(), possibleDirection), possibleDirection))
                .map(possibleCoordinateAndDirection ->
                        new AbstractMap.SimpleImmutableEntry<>(calculateMinStepsNeededToNearestFood(possibleCoordinateAndDirection.getKey(), turnData.board().food()), possibleCoordinateAndDirection.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .findFirst()
                .get();
    }

    private int calculateMinStepsNeededToNearestFood(Coordinate currentPosition, Coordinate[] food) {
        return Arrays.stream(food)
                .mapToInt(foodCoordinate ->
                                  Math.abs(foodCoordinate.x() - currentPosition.x())
                                + Math.abs(foodCoordinate.y() - currentPosition.y())
                )
                .min()
                .getAsInt();
    }

    private boolean validate(Coordinate coordinate, TurnData turnData) {
        return     isNotOutOfMap(coordinate, turnData.board().height()) 
                && isNotCollidingSnakes(coordinate, turnData.board().snakes());
    }

    private boolean isNotCollidingSnakes(Coordinate coordinate, Battlesnake[] snakes) {
        return Arrays.stream(snakes).flatMap(battlesnake -> Arrays.stream(battlesnake.body())).noneMatch(snakeCoordinate -> snakeCoordinate.equals(coordinate));
    }

    private boolean isNotOutOfMap(Coordinate coordinate, int boardHeight) {
        if(coordinate.x() < 0) {
            return false;
        }

        if(coordinate.x() >= boardHeight) {
            return false;
        }

        if(coordinate.y() < 0) {
            return false;
        }

        if(coordinate.y() >= boardHeight) {
            return false;
        }

        return true;
    }
}
