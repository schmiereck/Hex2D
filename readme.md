
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

# Reflection
Elektron nimmt Impuls des Photons auf,
es wird eine verschränkte Kopie des Elektrons mit dem zusätzlichen Impuls erzeugt.
Das kopierte Elektron will aber an seine ursprüngliche Position zurück
und sendet dafür ein Photon aus.

*Reflektieren wir mal über Reflexion – Ihre Frage*
https://scienceblogs.de/ihrefrage/2018/11/06/reflektieren-wir-mal-ueber-reflexion/

*Der Impuls des Photons*
https://qudev.phys.ethz.ch/static/content/science/BuchPhysikIV/PhysikIVch4.html

 ````
 p
. \.  .  .  .
.  e  .  .  .
.  .  .  .  .

.  .  .  .  .
.  e  .  .  .
.  . \.  .  .
.  .  e' .  .

      p'
.  . /.  .  .
.  e  .  .  .
.  .  .  .  .
.  .  .  .  .
````
# Rotation
NP, 
AP, BP, CP, AN, BN, CN

 ````

      bn  cp     
       \ /
   an---A---ap  
       / \
      cn  bp     

 ````
a   b   c   d Step:1
5            
4   1        
3   2        
2   3       
1   4       
    5
    4   1
    3   2
    2   3
    1   4
        5

a   b   c   d Step:3
5            
2   3        
    4   1       n2
    1   4       
        3   2   n2
            5
