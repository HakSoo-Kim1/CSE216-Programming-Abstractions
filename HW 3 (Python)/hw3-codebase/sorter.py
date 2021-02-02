from quadrilateral import Quadrilateral
from square import Square


class ShapeSorter:
    @staticmethod
    def sort(*args):
        lst = list(args)
        for each in args:
            if (not isinstance(each, Quadrilateral)):
                raise ValueError("Only Quadrilateral")
        return sorted(lst, key=lambda x: x.smallest_x())


