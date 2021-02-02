import unittest

from square import Square
from two_d_point import TwoDPoint
from quadrilateral import Quadrilateral


class TestSquare(unittest.TestCase):

    def test_snap(self):
        print("Testing snap method")
        A = Square(0, 0.0, -0.4, 0.0, -0.4, -0.4, 0, -0.4)
        B = Square(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        C = Square(3.8, 3.7, 1.7, 3.7, 1.7, 1.6, 3.8, 1.6)
        D = Square(0, 0, -10.5, 0, -10.5, -10.5, 0, -10.5)
        self.assertEqual(A.snap(), A)
        self.assertNotEqual(A.snap(), B)
        self.assertEqual(B.snap(), Quadrilateral(0,0,-5,0,-5,-5,0,-5))
        self.assertEqual(C.snap(), Quadrilateral(4,4,2,4,2,2,4,2))
        self.assertNotEqual(D.snap(), Square(0,0,-11,0,-11,-11,0,-11))
        print("Done testing snap method successfully")

    def test__is_member(self):
        print("Testing is_member method")
        B = Square(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        self.assertTrue((B._Square__is_member()))
        B._Quadrilateral__vertices = (TwoDPoint(0,0),TwoDPoint(0,0),TwoDPoint(0,0),TwoDPoint(0,0))
        self.assertFalse((B._Rectangle__is_member()))
        B._Quadrilateral__vertices = (TwoDPoint(0,0),TwoDPoint(-3.0,1.5),TwoDPoint(-3.0,-2.5),TwoDPoint(0,-1))
        self.assertFalse((B._Rectangle__is_member()))
        B._Quadrilateral__vertices = (TwoDPoint(0,0),TwoDPoint(-1,0),TwoDPoint(-1,-1),TwoDPoint(0,-1))
        self.assertTrue((B._Rectangle__is_member()))
        print("Done testing is_member method successfully")

    def test__str__(self):
        print("Testing __str__ method")
        A = Square(0, 0.0, -0.4, 0.0, -0.4, -0.4, 0, -0.4)
        B = Square(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        C = Square(3.8, 3.7, 1.7, 3.7, 1.7, 1.6, 3.8, 1.6)
        aStr = "I am a Square with TwoDpoint (0, 0) , TwoDpoint (-0.4, 0) , TwoDpoint (-0.4, -0.4) , TwoDpoint (0, -0.4)"
        bStr = "I am a Square with TwoDpoint (0, 0) , TwoDpoint (-5, 0) , TwoDpoint (-5, -5) , TwoDpoint (0, -5)"
        self.assertEqual(str(A), aStr)
        self.assertEqual(str(B), bStr)
        self.assertNotEqual(str(A), bStr)
        self.assertNotEqual(str(A),str(C))
        self.assertNotEqual(str(B), str(A))
        print("Done testing __str__ method successfully")


    def test___eq__(self):
        print("Testing __eq__ method")
        A = Square(0, 0.0, -0.4, 0.0, -0.4, -0.4, 0, -0.4)
        B = Square(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        C = Square(3.8, 3.7, 1.7, 3.7, 1.7, 1.6, 3.8, 1.6)
        self.assertTrue(A == A)
        self.assertFalse(A == B)
        self.assertTrue(B.__eq__(B))
        self.assertFalse(B.__eq__(C))
        print("Done testing __eq__ method successfully")




    def test___ne__(self):
        print("Testing __ne__ method")
        A = Square(0, 0.0, -0.4, 0.0, -0.4, -0.4, 0, -0.4)
        B = Square(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        C = Square(3.8, 3.7, 1.7, 3.7, 1.7, 1.6, 3.8, 1.6)
        self.assertFalse(A != A)
        self.assertTrue(A != B)
        self.assertFalse(B.__ne__(B))
        self.assertTrue(B.__ne__(C))
        print("Done testing __ne__ method successfully")

    def test_regular_round(self):
        self.assertTrue(Square.regular_round(-1.6) == -2)
        self.assertTrue(Square.regular_round(-1.4) == -1)
        self.assertTrue(Square.regular_round(1.6) == 2)
        self.assertTrue(Square.regular_round(1.4) == 1)






if __name__ == '__main__':
    unittest.main()