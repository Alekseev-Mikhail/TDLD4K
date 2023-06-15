package example

import tdld4k.GameSingleClient
import tdld4k.controllers.GameMoveMouseWithRobotInput
import tdld4k.math.Vector2Double
import tdld4k.world.GameAABBTile
import tdld4k.world.GameFullTile
import tdld4k.world.GameWorld
import java.awt.Color.*
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.KeyEvent.*
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val map="11111111111111111" +
            "1   4  1        1" +
            "1   4    1 111111" +
            "1   4 3  1      1" +
            "1    11111111   1" +
            "1          1    1" +
            "1111 1111  1  111" +
            "1          1  1 1" +
            "1   111    1  1 1" +
            "1               1" +
            "111111     1  111" +
            "1  1       1  1 1" +
            "1     111111    1" +
            "1  1            1" +
            "1  11  1111   111" +
            "1               1" +
            "11111111111111111"
    val mapWidth = 17
    val tileSize = 5

//    val polygon8 = GamePolygon(
//        listOf(
//            Point(0, 0),
//            Point(tileSize, 0),
//            Point(tileSize, tileSize),
//        )
//    )
//    val polygon9 = GamePolygon(
//        listOf(
//            Point(1, 1),
//            Point(tileSize - 1, 1),
//            Point(tileSize - 1, tileSize - 1),
//            Point(1, tileSize - 1),
//        )
//    )

    val tileTypes = mapOf(
        Pair('1', GameFullTile(GRAY)),
        Pair('2', GameFullTile(DARK_GRAY)),
        Pair('3', GameAABBTile(YELLOW, Vector2Double(1.0, 1.0), Vector2Double(4.0, 4.0))),
        Pair('4', GameAABBTile(ORANGE, Vector2Double(0.0, 0.0), Vector2Double(0.1, 5.0))),
    )
    val world = GameWorld(' ', BLUE, tileTypes, tileSize, map, mapWidth)
    val player = ExamplePlayer(
        isFreezeMovement = false,
        isFreezeRotation = false,
        6.0,
        0.1,
        60,
        10.0,
        10.0,
        0.0,
        70.0,
        0.1,
        400.0
    )
    val singleClient = GameSingleClient(player, world)
    val moveMouseWithRobotInput = GameMoveMouseWithRobotInput(isRobot = false, isFreezeMove = false)
    val keyboardController = ExampleKeyboardController(
        singleClient,
        player,
        true,
        moveMouseWithRobotInput,
        VK_W,
        VK_S,
        VK_A,
        VK_D,
        world,
    )
    val mouseController = ExampleMouseController(singleClient, player, moveMouseWithRobotInput)
    val firstCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        ImageIO.read(File("src/main/resources/example/cursor.png")), Point(0, 0), "first custom cursor"
    )
    val secondCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        ImageIO.read(File("src/main/resources/example/cursor1.png")), Point(0, 0), "second custom cursor"
    )

    mouseController.firstCustomCursor = firstCustomCursor
    mouseController.secondCustomCursor = secondCustomCursor
    singleClient.setCurrentCursor(firstCustomCursor)

    singleClient.initializationFrame(keyboardController, mouseController)
    singleClient.setFullscreenMode()

    singleClient.setInvisibleCursor()
    singleClient.playerFrame.title = "Example"
    singleClient.playerFrame.iconImage = ImageIO.read(File("src/main/resources/example/icon.jpg"))
    singleClient.playerFrame.isVisible = true
    singleClient.changeFrameSize(512, 512)
}