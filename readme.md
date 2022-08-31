
# Calculation of probability
## probability-naive
probability = numerator / denominator

## probability-maximum
Initial maximum probability is reduced using outgoing probabilities.

## probability-chained
Outgoing probabilities are stored in a chain.

Chained Bruch:

		4	10	1	5	2	
	100	25	2,5	2,5	0,5	0,25
		=100/4

Order changed, same Result:

		10	5	4	2	1	
	100	10	2	0,5	0,25	0,25

Multiply the numerators and then divide:

							=10*5*4*2*1
		10	5	4	2	1	400
	100	10	2	0,5	0,25	0,25	
							=100/400

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