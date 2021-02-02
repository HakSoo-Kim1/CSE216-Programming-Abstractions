type expr= 
    | Plus of {arg1: expr ; arg2: expr} 
    | Minus of {arg1: expr ; arg2: expr}
    | Mult of {arg1: expr ; arg2: expr}
    | Div of {arg1: expr ; arg2: expr}
    | Var of string         
    | Const of int;;
   
let rec evaluate expr = 
    match expr with
    | Plus {arg1 ; arg2 } -> evaluate arg1 + evaluate arg2
    | Minus {arg1 ; arg2 } -> evaluate arg1 - evaluate arg2
    | Mult {arg1 ; arg2 } -> evaluate arg1 * evaluate arg2
    | Div {arg1 ; arg2 } -> evaluate arg1 - evaluate arg2
    | Const number -> number
    | Var str -> failwith "no variable";;


