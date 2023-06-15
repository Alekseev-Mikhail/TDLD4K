package example

import tdld4k.SingleClient
import tdld4k.controllers.MoveMouseWithRobotInput
import tdld4k.math.Vector2Double
import tdld4k.world.AABBTile
import tdld4k.world.FullTile
import tdld4k.world.World
import java.awt.Color.BLUE
import java.awt.Color.DARK_GRAY
import java.awt.Color.GRAY
import java.awt.Color.ORANGE
import java.awt.Color.YELLOW
import java.awt.Point
import java.awt.Toolkit
import java.awt.event.KeyEvent.VK_A
import java.awt.event.KeyEvent.VK_D
import java.awt.event.KeyEvent.VK_S
import java.awt.event.KeyEvent.VK_W
import java.io.File
import javax.imageio.ImageIO

fun main() {
    val map = "11111111111111111" +
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

    val tileTypes = mapOf(
        Pair('1', FullTile(GRAY)),
        Pair('2', FullTile(DARK_GRAY)),
        Pair('3', AABBTile(YELLOW, Vector2Double(1.0, 1.0), Vector2Double(4.0, 4.0))),
        Pair('4', AABBTile(ORANGE, Vector2Double(0.0, 0.0), Vector2Double(0.1, 5.0))),
    )
    val world = World(map, mapWidth, ' ', BLUE, tileTypes, tileSize)
    val player = Player(
        10.0,
        10.0,
        0.0,
        70.0,
        0.1,
        400.0,
        6.0,
        0.4,
        60,
        isFreezeMovement = false,
        isFreezeRotation = false,
    )
    val singleClient = SingleClient(player, world)
    val moveMouseWithRobotInput = MoveMouseWithRobotInput(isRobot = false, isFreezeMove = false)
    val keyboardController = KeyboardController(
        world,
        VK_W,
        VK_S,
        VK_A,
        VK_D,
        singleClient,
        player,
        true,
        moveMouseWithRobotInput,
    )
    val mouseController = MouseController(singleClient, player, moveMouseWithRobotInput)
    val firstCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        ImageIO.read(File("src/main/resources/example/cursor.png")),
        Point(0, 0),
        "first custom cursor",
    )
    val secondCustomCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        ImageIO.read(File("src/main/resources/example/cursor1.png")),
        Point(0, 0),
        "second custom cursor",
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
