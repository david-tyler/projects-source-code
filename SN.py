import pygame
from pygame.locals import *  # all the key action events
import time
import random

SIZE = 40  # unit size of images
BHEIGHT = 800  # board width
BWIDTH = 850  # board height


class Apple:
    def __init__(self, parent_screen):
        self.parent_screen = parent_screen
        self.apple = pygame.image.load("resources/apple.png").convert()
        self.x = random.randint(1, int((BWIDTH / SIZE))) * SIZE
        self.y = random.randint(1, int((BHEIGHT / SIZE))) * SIZE

    def move(self):
        self.x = random.randint(1, int((BWIDTH / SIZE)-4)) * SIZE
        self.y = random.randint(1, int((BHEIGHT / SIZE)-1)) * SIZE
        self.draw()

    def draw(self):
        self.parent_screen.blit(self.apple, (self.x, self.y))  # draws the apple
        pygame.display.flip()  # updates display


class Snake:
    def __init__(self, window, length):
        self.length = length  # length of the snake or number of blocks
        self.parent_screen = window
        self.body = pygame.image.load("resources/dot.png").convert()
        self.head = pygame.image.load("resources/head.png").convert()  # loads image
        self.x = [SIZE] * length
        self.y = [SIZE] * length
        self.direction = "down"
        self.cant = "up"

    def increase_length(self):
        self.length += 1
        self.x.append(-1)
        self.y.append(-1)

    def draw(self):
        for i in range(self.length):
            self.parent_screen.blit(self.head, (self.x[0], self.y[0]))  # draws the head
            # if(i>0):
            self.parent_screen.blit(self.body, (self.x[i], self.y[i]))  # draws the body
        pygame.display.flip()  # updates display

    def move_left(self):
        self.direction = "left"
        self.cant = "right"
        self.draw()

    def move_right(self):
        self.direction = "right"
        self.cant = "left"
        self.draw()

    def move_down(self):
        self.direction = "down"
        self.cant = "up"
        self.draw()

    def move_up(self):
        self.direction = "up"
        self.cant = "down"
        self.draw()

    def walk(self):
        for i in range(self.length - 1, 0, -1):
            self.x[i] = self.x[i - 1]
            self.y[i] = self.y[i - 1]
        if self.direction == "up":
            self.y[0] -= 10
        if self.direction == "down":
            self.y[0] += 10
        if self.direction == "left":
            self.x[0] -= 10
        if self.direction == "right":
            self.x[0] += 10
        self.draw()


class Game:
    def __init__(self):
        pygame.init()
        self.window = pygame.display.set_mode((BWIDTH, BHEIGHT))  # creates window
        self.snake = Snake(self.window, 1)
        self.snake.draw()
        self.apple = Apple(self.window)
        self.apple.draw()
        self.score = 0

    def reset(self):
        self.snake = Snake(self.window,1)
        self.apple = Apple(self.window)
        self.score=0

    def render_background(self):
        bg = pygame.image.load("resources/background.jpg")
        self.window.blit(bg,(0,0))

    def CheckCollision(self):
        for i in range(self.snake.length - 1, 0, -1):
            if (self.snake.x[0] == self.snake.x[i] and self.snake.y[0] == self.snake.y[i]):
                raise "Game over"
        if self.snake.x[0] <= 0:
            raise "Game over"
        if self.snake.x[0] >= BWIDTH:
            raise "Game over"
        if self.snake.y[0] >= BHEIGHT:
            raise "Game over"
        if self.snake.y[0] <= 0:
            raise "Game over"
        if (self.snake.x[0] == self.apple.x and self.snake.y[0] == self.apple.y):
            self.snake.increase_length()
            self.apple.move()
            self.score += 1


    def play(self):
        self.render_background()
        self.snake.walk()
        self.apple.draw()
        self.display_score()
        pygame.display.flip()
        self.CheckCollision()

    def show_game_over(self):
        self.render_background()
        font = pygame.font.SysFont("arial", 30)
        line1 = font.render(f"Game is over: Your Score is: {self.score}", True, "red")
        self.window.blit(line1,(BWIDTH/4-100,BHEIGHT/2))
        line2 = font.render("To play again press Enter.", True, 'white')
        self.window.blit(line2,(BWIDTH/4-100,(BHEIGHT/2)+50))
        pygame.display.flip()

    def run(self):
        running = True
        pause = False
        while (running):
            for event in pygame.event.get():
                if event.type == KEYDOWN:
                    if event.key == K_ESCAPE:  # if you hit escape closes window
                        running = False
                    if event.key == K_RETURN:
                        pause = False
                    if not pause:
                        if event.key == K_UP:
                            self.snake.move_up()
                        if event.key == K_DOWN:
                            self.snake.move_down()
                        if event.key == K_LEFT:
                            self.snake.move_left()
                        if event.key == K_RIGHT:
                            self.snake.move_right()
                elif event.type == QUIT:  # if you hit the x button closes window
                    running = False
            try:
                if not pause:
                    self.play()
            except Exception as e:
                self.show_game_over()
                pause = True
                self.reset()
            time.sleep(0.07)  # every 0.07 sec the snake moves on its own
    def display_score(self):
        font = pygame.font.SysFont("arial", 30)
        score = font.render(f"Score: {self.score}", True, "orange")
        self.window.blit(score, (700, 10))


def manual():
    print("In this game you will be controlling a snake.")
    print("You will need to use the arrow keys to control the snake.")
    print("Be careful not to touch the border or cross your body or you lose!")
    print("Your goal is to eat as many apples as you can in order to grow as long as possible")
    print("You have unlimited time, see if you can set an unbeatable score!")
    ready = input("Ready? (Y/N): ")
    return ready
def start():
    if (manual()=='Y'):
        game = Game()
        game.run()


