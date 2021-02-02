(*Part 1*)
  (* 1 *)
let rec pow x n = 
  if (n = 0) then 
    1
  else 
    x * pow x (n-1);;

let rec float_pow x n =
  if (n = 0) then 
  1.0
  else 
  x *. float_pow x (n-1);;

  (* 2 *)
let rec compress lst =
  match lst with 
  | h1 :: h2 :: t -> if (h1 = h2) then compress (h2 :: t) 
                     else h1:: compress (h2::t)
  | lst -> lst;;

  (* 3 *)
let rec remove_if lst f =
  match lst with
  | [] -> []
  | h :: t -> if (f h = true) then remove_if t f
              else h :: remove_if t f;;

  (* 4 *)
let slice lst x y = 
  let rec slicefront z lst=
    match lst with
    | [] -> []
    | h :: t -> if (x>y) then []
                else if (z = 0) then []
                else h :: slicefront (z-1) t
      in let rec sliceback z lst =
            match lst with
            | [] -> []
            | h :: t -> if (z = 0) then h :: t 
                        else sliceback (z-1) t 
        in slicefront (y - x) (sliceback x lst);;

  (* 5 *)
let get_head lst =
  match lst with
  | [] -> failwith "empty list"
  | h :: _ -> h;;

let addElement lst element =
  match lst with 
  | [] -> [element]
  | lst -> lst @ [element] ;;

let rec categorize f each final_list = 
  match final_list with 
  | [] | [[]] -> [[each]]
  | h::t -> if f each (get_head h) then ( addElement h each ) :: t 
            else h::(categorize f each t);;

let equivs f lst = 
  let rec separate f original_list final_list  = match original_list with
	| [] -> final_list
  | h::t -> separate f t (categorize f h final_list )
    in separate f lst [[]];;

  (* 6 *)
let rec is_prime n =
  let rec divisor d = 
    if (n = d) then true
    else if (n mod d = 0) then false
    else (divisor ( d + 1))
      in n <> 1 && divisor 2;;

let goldbachpair n =
  let rec aux d =
    if (is_prime d && is_prime (n - d)) then (d, n-d)
    else aux (d+1)
     in aux 2;;

  (* 7 *)
let rec equiv_on f1 f2 lst = 
    match lst with 
    | [] -> true
    | h :: t -> if ( (f1 h) = (f2 h) ) then equiv_on f1 f2 t 
                else false;;

  (* 8 *)
let rec pairwisefilter cmp lst = 
  match lst with 
  | first :: second :: t -> (cmp first second) :: (pairwisefilter cmp t)
  | lst -> lst;;

  (* 9 *)
let rec polynomial lst y =
  match lst with
  | [] -> 0
  | (a,b) :: t -> let one = int_of_float((float_of_int y) **  (float_of_int b) *.  (float_of_int a))
     in  one + polynomial t y;;



   (* 10 *)
let rec powerset lst = 
  let rec map f lst =
    match lst with 
    | [] -> []
    | h :: t -> (f h) :: (map f t)
    in
      match lst with	
      | [] -> [lst]
      | h :: t -> let f = fun back -> h :: back 
        in powerset t @ (map f (powerset t)) ;;
      