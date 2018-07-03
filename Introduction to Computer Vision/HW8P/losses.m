%Randyll Bearer:    HW8P:   Compute and Print Types of Loss
%x = 25x1
%w = 4x25
%s = 4x1

%Compile the scores for first set of weights
s11 = W1*x1;
s12 = W1*x2;
s13 = W1*x3;
s14 = W1*x4;

%Compute hinge-losses for first set of weights
hingeLoss1 = hinge_loss(s11,1);
hingeLoss2 = hinge_loss(s12,2);
hingeLoss3 = hinge_loss(s13,3);
hingeLoss4 = hinge_loss(s14,4);

weight1HingeLoss = (hingeLoss1+hingeLoss2+hingeLoss3+hingeLoss4)/4; %Normalize
display("Weight1 Hinge-Loss = " + weight1HingeLoss);

%Compute cross-entropy losses for first set of weights
crossEntropyLoss1 = cross_entropy_loss(s11, 1);
crossEntropyLoss2 = cross_entropy_loss(s12, 2);
crossEntropyLoss3 = cross_entropy_loss(s13, 3);
crossEntropyLoss4 = cross_entropy_loss(s14, 4);
weight1CrossEntropyLoss = (crossEntropyLoss1+crossEntropyLoss2+crossEntropyLoss3+crossEntropyLoss4)/4; %Normalize
display("Weight1 Cross-Entropy-Loss = " + weight1CrossEntropyLoss);

%Compile the scores for second set of weights
s21 = W2*x1;
s22 = W2*x2;
s23 = W2*x3;
s24 = W2*x4;

%Compute hinge-losses for second set of weights
hingeLoss1 = hinge_loss(s21,1);
hingeLoss2 = hinge_loss(s22,2);
hingeLoss3 = hinge_loss(s23,3);
hingeLoss4 = hinge_loss(s24,4);

weight2HingeLoss = (hingeLoss1+hingeLoss2+hingeLoss3+hingeLoss4)/4; %Normalize
display("Weight2 Hinge-Loss = " + weight2HingeLoss);

%Compute cross-entropy losses for second set of weights
crossEntropyLoss1 = cross_entropy_loss(s21, 1);
crossEntropyLoss2 = cross_entropy_loss(s22, 2);
crossEntropyLoss3 = cross_entropy_loss(s23, 3);
crossEntropyLoss4 = cross_entropy_loss(s24, 4);
weight2CrossEntropyLoss = (crossEntropyLoss1+crossEntropyLoss2+crossEntropyLoss3+crossEntropyLoss4)/4; %Normalize
display("Weight2 Cross-Entropy-Loss = " + weight2CrossEntropyLoss);

%Compile the scores for third set of weights
s31 = W3*x1;
s32 = W3*x2;
s33 = W3*x3;
s34 = W3*x4;

%Compute hinge-losses for third set of weights
hingeLoss1 = hinge_loss(s31,1);
hingeLoss2 = hinge_loss(s32,2);
hingeLoss3 = hinge_loss(s33,3);
hingeLoss4 = hinge_loss(s34,4);

weight3HingeLoss = (hingeLoss1+hingeLoss2+hingeLoss3+hingeLoss4)/4; %Normalize
display("Weight3 Hinge-Loss = " + weight3HingeLoss);

%Compute cross-entropy losses for third set of weights
crossEntropyLoss1 = cross_entropy_loss(s31, 1);
crossEntropyLoss2 = cross_entropy_loss(s32, 2);
crossEntropyLoss3 = cross_entropy_loss(s33, 3);
crossEntropyLoss4 = cross_entropy_loss(s34, 4);
weight3CrossEntropyLoss = (crossEntropyLoss1+crossEntropyLoss2+crossEntropyLoss3+crossEntropyLoss4)/4; %Normalize
display("Weight3 Cross-Entropy-Loss = " + weight3CrossEntropyLoss);

%End of File