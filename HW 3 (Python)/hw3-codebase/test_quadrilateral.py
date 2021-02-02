import unittest
from quadrilateral import Quadrilateral



class TestQuadrilateral(unittest.TestCase):

    def test_side_lengths(self):
        print("Testing side_lengths method")
        A = Quadrilateral(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Quadrilateral(0.0, 0.0, -3.0, 1, -3.0, -2, 0.0, -1.0)
        aSide = (3.5,7.8,3.5,7.8)
        bSide = (3.16228, 1.0, 3.16228, 3.0)
        self.assertEqual(aSide, A.side_lengths())
        self.assertEqual(bSide, B.side_lengths())
        self.assertNotEqual(aSide, (0,0,0,0))
        print("Done testing side_lengths method successfully")

    def test_smallest_x(self):
        print("Testing smallest_x method")
        A = Quadrilateral(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Quadrilateral(0.0, 0.0, -3.0, 1, -3.0, -2, 0.0, -1.0)
        self.assertEqual(A.smallest_x(), -3.5)
        self.assertNotEqual(B.smallest_x(), -1.0)
        print("Done testing smallest_x method successfully")

    def test__str__(self):
        print("Testing __str__ method")
        A = Quadrilateral(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Quadrilateral(0.0, 0.0, -3.0, 1, -3.0, -2, 0.0, -1.0)
        aStr = "I am a Quadrilateral with TwoDpoint (0, 0) , TwoDpoint (-3.5, 0) , TwoDpoint (-3.5, -7.8) , TwoDpoint (0, -7.8)"
        bStr = "I am a Quadrilateral with TwoDpoint (0, 0) , TwoDpoint (-3, 1) , TwoDpoint (-3, -2) , TwoDpoint (0, -1)"
        randStr = "I am a Quadrilateral with TwoDpoint (0, 0) , TwoDpoint (0, 1) , TwoDpoint (0, 0) , TwoDpoint (0, 0)"
        self.assertEqual(str(A), aStr)
        self.assertEqual(str(B), bStr)
        self.assertNotEqual(str(B), randStr)
        print("Done testing __str__ method successfully")

    def test___eq__(self):
        print("Testing __eq__ method")
        A = Quadrilateral(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Quadrilateral(0.0, 0.0, -3.0, 1, -3.0, -2, 0.0, -1.0)
        C = Quadrilateral(0, 0 , -1, 0, -1, -1, 0, -1 )
        self.assertTrue(A == A)
        self.assertFalse(A == B)
        self.assertTrue(B.__eq__(B))
        self.assertFalse(B.__eq__(C))
        print("Done testing __eq__ method successfully")

    def test___ne__(self):
        print("Testing __ne__ method")
        A = Quadrilateral(0, 0, -3.5, 0.0, -3.5, -7.8, 0, -7.8)
        B = Quadrilateral(0.0, 0.0, -3.0, 1, -3.0, -2, 0.0, -1.0)
        C = Quadrilateral(0, 0 , -1, 0, -1, -1, 0, -1 )
        self.assertFalse(A != A)
        self.assertTrue(A != B)
        self.assertFalse(B.__ne__(B))
        self.assertTrue(B.__ne__(C))
        print("Done testing __ne__ method successfully")










if __name__ == '__main__':
    unittest.main()