from rectangle import Rectangle
from quadrilateral import Quadrilateral
from two_d_point import TwoDPoint
import math


class Square(Rectangle):

    def __init__(self, *floats):
        super().__init__(*floats)
        if not self.__is_member():
            raise TypeError("A square cannot be formed by the given coordinates.")

    def __is_member(self):

        if ((self.vertices[0] == self.vertices[1]) or (self.vertices[0] == self.vertices[2]) or (self.vertices[0] == self.vertices[3]) or (self.vertices[1] == self.vertices[2]) or (self.vertices[1] == self.vertices[3]) or (self.vertices[2] == self.vertices[3])):
            return False
        side1 = round(self.side_lengths()[0],5)
        side2 = round(self.side_lengths()[1],5)
        side3 = round(self.side_lengths()[2],5)
        side4 = round(self.side_lengths()[3],5)
        if ((side1 == side3) and (side2 == side4)):
            if ( (round((pow(side1,2) + pow(side2,2)),5)) == ( round((pow(side3,2) + pow(side4,2)),5))):
                if ((side1 == side3 == side2 == side4)):
                    return True
        return False

    def snap(self):
        """Snaps the sides of the square such that each corner (x,y) is modified to be a corner (x',y') where x' is the
                integer value closest to x and y' is the integer value closest to y. This, of course, may change the shape to a
                general quadrilateral, hence the return type. The only exception is when the square is positioned in a way where
                this approximation will lead it to vanish into a single point. In that case, a call to snap() will not modify
                this square in any way."""
        newPoint1 = TwoDPoint(Square.regular_round(self.vertices[0].x),Square.regular_round(self.vertices[0].y))
        newPoint2 = TwoDPoint(Square.regular_round(self.vertices[1].x),Square.regular_round(self.vertices[1].y))
        newPoint3 = TwoDPoint(Square.regular_round(self.vertices[2].x),Square.regular_round(self.vertices[2].y))
        newPoint4 = TwoDPoint(Square.regular_round(self.vertices[3].x),Square.regular_round(self.vertices[3].y))
        if (newPoint1 == newPoint2 or  newPoint1 == newPoint3 or newPoint1 == newPoint4 or newPoint2 == newPoint3 or newPoint2 == newPoint4  or newPoint3 == newPoint4):
            return self
        return Quadrilateral(Square.regular_round(self.vertices[0].x),Square.regular_round(self.vertices[0].y), Square.regular_round(self.vertices[1].x), Square.regular_round(self.vertices[1].y), Square.regular_round(self.vertices[2].x), Square.regular_round(self.vertices[2].y),Square.regular_round(self.vertices[3].x),Square.regular_round(self.vertices[3].y)) # TODO



    def __str__(self) -> str:
        return 'I am a Square with ' + str(self.vertices[0]) + " , " + str(self.vertices[1]) + " , " + str(
            self.vertices[2]) + " , " + str(self.vertices[3])

    def __eq__(self, other: object) -> bool:
        if isinstance(other, Square):
            if ( (self.vertices[0] == other.vertices[0]) and (self.vertices[1] == other.vertices[1]) and (self.vertices[2] == other.vertices[2]) and (self.vertices[3] == other.vertices[3])):
                return True
        return False  # TODO

    def __ne__(self, other: object) -> bool:
        return not self.__eq__(other)

    @staticmethod
    def regular_round(n):
        if (n - math.floor(n) < 0.5):
            return math.floor(n)
        return math.ceil(n)




