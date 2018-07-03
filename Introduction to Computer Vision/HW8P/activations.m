%Randyll Bearer:  HW8P
%Part 1: Network Activations
%-----------------------------------
%Initialize Variable
%w121 == Layer 1, To 2, From 1
x1 = 10;
x2 = 1;
x3 = 2;
x4 = 3;
w111 = 0.5;
w112 = .6;
w113 = .4;
w114 = .3;
w121 = 0.02;
w122 = 0.25;
w123 = 0.4;
w124 = 0.3;
w131 = 0.82;
w132 = 0.1;
w133 = 0.35;
w134 = 0.3;
w211 = 0.7;
w212 = 0.45;
w213 = 0.5;
w221 = 0.17;
w222 = 0.9;
w223 = 0.8;
y1 = 0;
y2 = 0;
z1 = 0;
z2 = 0;
z3 = 0;
z4 = 0;

inputs = [x1, x2, x3, x4];
weights1 = [w111, w112, w113, w114; w121, w122, w123, w124; 
            w131, w132, w133, w134];
weights2 = [w211, w212, w213; w221, w222, w223];

%Calculate value of z2
z2 = tanh( (x1*w121)+(x2*w122)+(x3*w123)+(x4*w124) );
display("Value of z2 = " + z2);

%Calculate value of y1
%Input-Hidden RELU
z1 = max(0, ( (x1*w111)+(x2*w112)+(x3*w113)+(x4*w114) ) );
z2 = max(0, ( (x1*w121)+(x2*w122)+(x3*w123)+(x4*w124) ) );
z3 = max(0, ( (x1*w131)+(x2*w132)+(x3*w133)+(x4*w134) ) );
%Hidden-Output Sigmoid
y1 =  1/(1+exp(-( (z1*w211)+(z2*w212)+(z3*w213) )));
y2 =  1/(1+exp(-( (z1*w221)+(z2*w222)+(z3*w223) )));
display("Value of y1 = " + y1);




%End of File