type bool_expr =
  | Lit of string
  | Not of bool_expr
  | And of bool_expr * bool_expr
  | Or of bool_expr * bool_expr;;

let truth_table first second boolean_expr = 
  let rec solve first first_bool second second_bool boolean_expr =
    match boolean_expr with 
    |Lit boolean_expr -> if  boolean_expr = first then first_bool
                         else if  boolean_expr = second then second_bool
                         else failwith "not valid"
    |Not boolean_expr -> not(solve first first_bool second second_bool boolean_expr)
    |And (boolean_expr1 , boolean_expr2) -> solve first first_bool second second_bool boolean_expr1 && solve first first_bool second second_bool boolean_expr2
    |Or  (boolean_expr1 , boolean_expr2) -> solve first first_bool second second_bool boolean_expr1 || solve first first_bool second second_bool boolean_expr2
     in
         [(true, true, solve first true second true boolean_expr );
          (true, false, solve first true second false boolean_expr );
          (false, true, solve first false second true boolean_expr );
          (false, false, solve first false second false boolean_expr) ];;


         