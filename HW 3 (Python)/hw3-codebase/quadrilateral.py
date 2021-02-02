

from two_d_point import TwoDPoint



class Quadrilateral:

    def __init__(self, *floats):
        if not (len(list(floats)) == 8):
            raise TypeError("A {} cannot be formed by the given coordinates.".format(self.__class__.__name__))
        points = TwoDPoint.from_coordinates(list(floats))
        self.__vertices = tuple(points[0:4])
        if ((self.vertices[0] == self.vertices[1]) or (self.vertices[0] == self.vertices[2]) or (self.vertices[0] == self.vertices[3]) or (self.vertices[1] == self.vertices[2]) or (self.vertices[1] == self.vertices[3]) or (self.vertices[2] == self.vertices[3])):
            raise TypeError("A {} cannot be formed by the given coordinates.".format(self.__class__.__name__))


    @property
    def vertices(self):
        return self.__vertices

    def side_lengths(self):
        side1 = pow(pow(self.__vertices[1].x - self.__vertices[0].x , 2) + pow(self.__vertices[1].y - self.__vertices[0].y , 2),0.5)
        side1 = round(side1,5)
        side2 = pow(pow(self.__vertices[0].x - self.__vertices[3].x , 2) + pow(self.__vertices[0].y - self.__vertices[3].y , 2),0.5)
        side2 = round(side2,5)
        side3 = pow(pow(self.__vertices[3].x - self.__vertices[2].x , 2) + pow(self.__vertices[3].y - self.__vertices[2].y , 2),0.5)
        side3 = round(side3,5)
        side4 = pow(pow(self.__vertices[2].x - self.__vertices[1].x , 2) + pow(self.__vertices[2].y - self.__vertices[1].y , 2),0.5)
        side4 = round(side4,5)
        return side1, side2, side3, side4  # TODO

    def smallest_x(self):
        lst = [self.vertices[0].x, self.vertices[1].x, self.vertices[2].x, self.vertices[3].x]
        min_num = min(lst)
        return min_num
            # TODO

    def __str__(self) -> str:
        return 'I am a Quadrilateral with ' + str(self.vertices[0]) + " , "+ str(self.vertices[1]) + " , " + str(self.vertices[2]) + " , " + str(self.vertices[3])

    def __eq__(self, other: object) -> bool:
        if isinstance(other, Quadrilateral):
            if ( (self.vertices[0] == other.vertices[0]) and (self.vertices[1] == other.vertices[1]) and (self.vertices[2] == other.vertices[2]) and (self.vertices[3] == other.vertices[3])):
                return True
        return False  # TODO

    def __ne__(self, other: object) -> bool:
        return not self.__eq__(other)











