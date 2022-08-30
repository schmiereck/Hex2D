
# Calculation of probability
## probability-naive
probability = numerator / denominator

## probability-maximum
Initial maximum probability is reduced using outgoing probabilities.

## probability-chained
Outgoing probabilities are stored in a chain.

# Direction
In between four axis:
````
        *
      \   /
       \ /
--------+--------

        *
      . | .
    .   |   .
  .     |     .  
+---+---+---+---+---+
0   25  50  25  0

````

````
         *
      \   /
       \ /
--------+--------

          *
        . | .
      .   |   .
    .     |     .  
+---+---+---+---+---+
0   12,5    37,5
        37,5    12,5

````