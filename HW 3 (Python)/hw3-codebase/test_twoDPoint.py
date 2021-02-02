import unittest
from two_d_point import TwoDPoint


class TestTwoDPoint(unittest.TestCase):


    def test_from_coordinates(self):
        print("Testing _from_coordinates method")
        c = TwoDPoint.from_coordinates([0, 0, -3.5,0.0])
        d = [TwoDPoint(0,0),TwoDPoint(-3.5,0.0)]
        e = [TwoDPoint(0,0),TwoDPoint(-3.5, 3.5)]
        self.assertEqual(c,d)
        self.assertNotEqual(d,e)
        with self.assertRaises(Exception) as assertStatement: TwoDPoint.from_coordinates([1, 1, 1])
        self.assertEqual("Odd number of floats given to build a list of 2-d points",str(assertStatement.exception))
        print("Done testing _from_coordinates successfully")


    def test___eq__(self):
        print("Testing __eq__ method")
        twoD1 = TwoDPoint(0,0)
        twoD2 = TwoDPoint(0,0)
        twoD3 = TwoDPoint(1,1)
        self.assertTrue(twoD1 == twoD2)
        self.assertFalse(twoD1 == twoD3)
        self.assertTrue(twoD1.__eq__(twoD2))
        self.assertFalse(twoD1.__eq__(twoD3))

        print("Done testing __eq__ method successfully")

    def test___ne__(self):
        print("Testing __ne__ method")
        twoD1 = TwoDPoint(0,0)
        twoD2 = TwoDPoint(0,0)
        twoD3 = TwoDPoint(1,1)
        self.assertFalse(twoD1 != twoD2)
        self.assertTrue(twoD1 != twoD3)
        self.assertFalse(twoD1.__ne__(twoD2))
        self.assertTrue(twoD1.__ne__(twoD3))
        print("Done testing __ne__ method successfully")

    def test__str__(self):
        print("Testing __str__ method")
        twoD1 = TwoDPoint(0, 0)
        self.assertEqual(str(twoD1), "TwoDpoint (0, 0)")
        self.assertNotEqual(str(twoD1), "TwoDpoint (1, 1)")
        print("Done testing __str__ method successfully")

    def test__add__(self):
        print("Testing __add__ method")
        twoD1 = TwoDPoint(1, 0)
        twoD2 = TwoDPoint(0, 1)
        twoD3 = TwoDPoint(1, 1)
        self.assertEqual(twoD1.__add__(twoD2), twoD3)
        self.assertEqual(twoD1 + twoD2, twoD3)
        self.assertNotEqual(twoD1.__add__(twoD3), twoD3)
        self.assertNotEqual(twoD1 + twoD1, twoD3)
        with self.assertRaises(ValueError) as assertStatement: twoD1 + "string"
        self.assertEqual("Addition is possibile only between TwoDPoint",str(assertStatement.exception))
        print("Done testing __add__ method successfully")

    def test__sub__(self):
        print("Testing __add__ method")
        twoD1 = TwoDPoint(1, 1)
        twoD2 = TwoDPoint(1, 0)
        twoD3 = TwoDPoint(0, 1)
        self.assertEqual(twoD1.__sub__(twoD2), twoD3)
        self.assertEqual(twoD1 - twoD2, twoD3)
        self.assertNotEqual(twoD1.__sub__(twoD3), twoD3)
        self.assertNotEqual(twoD1 - twoD1, twoD3)
        with self.assertRaises(ValueError) as assertStatement: twoD1 - "string"
        self.assertEqual("Subtraction is possibile only between TwoDPoint", str(assertStatement.exception))
        print("Done testing __add__ method successfully")

        # self.assertEqual(twoD1 == twoD2)












if __name__ == '__main__':
    unittest.main()