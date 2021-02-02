from quadrilateral import Quadrilateral
from two_d_point import TwoDPoint


class Rectangle(Quadrilateral):

    def __init__(self, *floats):
        super().__init__(*floats)
        if not self.__is_member():
            raise TypeError("A {} cannot be formed by the given coordinates.".format(self.__class__.__name__))

    def __is_member(self):
        """Returns True if the given coordinates form a valid rectangle, and False otherwise."""
        if ((self.vertices[0] == self.vertices[1]) or (self.vertices[0] == self.vertices[2]) or (self.vertices[0] == self.vertices[3]) or (self.vertices[1] == self.vertices[2]) or (self.vertices[1] == self.vertices[3]) or (self.vertices[2] == self.vertices[3])):
            return False
        side1 = round(self.side_lengths()[0],5)
        side2 = round(self.side_lengths()[1],5)
        side3 = round(self.side_lengths()[2],5)
        side4 = round(self.side_lengths()[3],5)
        if ((side1 == side3) and (side2 == side4)):
            if ( (round((pow(side1,2) + pow(side2,2)),5)) == ( round((pow(side3,2) + pow(side4,2)),5))):
                return True
        return False


    def center(self):
        newX = self.vertices[0].x + self.vertices[1].x + self.vertices[2].x + self.vertices[3].x
        newY = self.vertices[0].y + self.vertices[1].y + self.vertices[2].y + self.vertices[3].y

        return TwoDPoint(newX/4.0, newY/4.0)  # TODO

    def area(self):
        return self.side_lengths()[0] * self.side_lengths()[1]  # TODO

    def __str__(self) -> str:
        return 'I am a Rectangle with ' + str(self.vertices[0]) + " , "+ str(self.vertices[1]) + " , " + str(self.vertices[2]) + " , " + str(self.vertices[3])


    def __eq__(self, other: object) -> bool:
        if isinstance(other, Rectangle):
            if ((self.vertices[0] == other.vertices[0]) and (self.vertices[1] == other.vertices[1]) and (
                    self.vertices[2] == other.vertices[2]) and (self.vertices[3] == other.vertices[3])):
                return True
        return False  # TODO


    def __ne__(self, other: object) -> bool:
        return not self.__eq__(other)
