import unittest
from rectangle import Rectangle
from two_d_point import TwoDPoint




class TestRectangle(unittest.TestCase):

    def test_center(self):
        print("Testing center method")
        A = Rectangle(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Rectangle(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        aCenter = TwoDPoint(-1.75, -3.9)
        bCenter = TwoDPoint(-2.5, -2.5)
        self.assertEqual(aCenter, A.center())
        self.assertEqual(bCenter, B.center())
        self.assertNotEqual(A.center(),TwoDPoint(0,0))
        print("Done testing center method successfully")



    def test_area(self):
        print("Testing area method")
        A = Rectangle(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Rectangle(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        self.assertEqual(A.area(), 27.3)
        self.assertEqual(B.area(), 25)
        self.assertNotEqual(B.area(), 0)
        self.assertEqual(Rectangle(8, 8, 3, 8, 3, 5, 8, 5).area(), 15)
        print("Done testing area method successfully")

    def test__is_member(self):
        print("Testing is_member method")
        B = Rectangle(4, 2, -4, 2, -4, 0, 4, 0)
        self.assertTrue((B._Rectangle__is_member()))
        B._Quadrilateral__vertices = (TwoDPoint(0,0),TwoDPoint(0,0),TwoDPoint(0,0),TwoDPoint(0,0))
        self.assertFalse((B._Rectangle__is_member()))
        B._Quadrilateral__vertices = (TwoDPoint(0,0),TwoDPoint(-3.5,0),TwoDPoint(-3.5,-7.8),TwoDPoint(0,-7.8))
        self.assertTrue((B._Rectangle__is_member()))
        print("Done testing is_member method successfully")


    def test__str__(self):
        print("Testing __str__ method")
        A = Rectangle(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Rectangle(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        aStr = "I am a Rectangle with TwoDpoint (0, 0) , TwoDpoint (-3.5, 0) , TwoDpoint (-3.5, -7.8) , TwoDpoint (0, -7.8)"
        bStr = "I am a Rectangle with TwoDpoint (0, 0) , TwoDpoint (-5, 0) , TwoDpoint (-5, -5) , TwoDpoint (0, -5)"
        cStr = "I am a Rectangle with TwoDpoint (0, 0) , TwoDpoint (-10, 0) , TwoDpoint (-10, -10) , TwoDpoint (0, -10)"
        self.assertEqual(str(A), aStr)
        self.assertEqual(str(B), bStr)
        self.assertNotEqual(str(B),str(A))
        self.assertNotEqual(str(B), cStr)
        print("Done testing __str__ method successfully")


    def test___eq__(self):
        print("Testing __eq__ method")
        A = Rectangle(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Rectangle(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        C = Rectangle(0, 0 , -1, 0, -1, -1, 0, -1 )
        self.assertTrue(A == A)
        self.assertFalse(A == B)
        self.assertTrue(B.__eq__(B))
        self.assertFalse(B.__eq__(C))
        print("Done testing __eq__ method successfully")

    def test___ne__(self):
        print("Testing __ne__ method")
        A = Rectangle(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Rectangle(0, 0.0, -5, 0.0, -5, -5, 0, -5)
        C = Rectangle(0, 0, -1, 0, -1, -1, 0, -1)
        self.assertFalse(A != A)
        self.assertTrue(A != B)
        self.assertFalse(B.__ne__(B))
        self.assertTrue(B.__ne__(C))
        print("Done testing __ne__ method successfully")



if __name__ == '__main__':
    unittest.main()