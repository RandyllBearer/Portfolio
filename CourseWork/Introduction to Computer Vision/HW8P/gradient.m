%Randyll Bearer:    HW8P:   Gradients
%--------------Learning Rate h=0.0001-------------------------
%Convert W1 into a vector
W1Backup = W1;
[W1X, W1Y] = size(W1);
numElements = W1X*W1Y;

%Initialize loss variables & output
currentLoss = 0;
previousLoss = 2.6194;
output1 = zeros(100,1); %To hold our gradients

%Loop over every element in the vector
i = 1;
while i <= numElements
    %To make change 1 at a time, restore original Weight vecotr
    W1 = W1Backup;
    
    %Convert W1 to vector
    W1 = W1(:);
    
    %Add h to a single element in W1 vector
    W1(i) = W1(i)+0.0001;
    
    %Convert W back to matrix
    W1 = reshape(W1,[4,25]);
    
    %Calculate current loss
    score = W1*x1;
    currentLoss = hinge_loss(score, 1);
    
    %Compute new gradient derivative
    newDerivative = (currentLoss - previousLoss)/0.0001;
    
    %Update output vector
    output1(i) = newDerivative;
    
    %Update current/previous
    previousLoss = currentLoss;
    
    i = i + 1; %repeat until i > 100
    
end

display("Outputting resultant gradient derivative vector with learning rate h=0.0001:");
display(output1);



%--------------Learning Rate h=0.001-------------------------
%Convert W1 into a vector
W1Backup = W1;
[W1X, W1Y] = size(W1);
numElements = W1X*W1Y;

%Initialize loss variables & output
currentLoss = 0;
previousLoss = 2.6194;
output2 = zeros(100,1); %To hold our gradients

%Loop over every element in the vector
i = 1;
while i <= numElements
    %To make change 1 at a time, restore original Weight vecotr
    W1 = W1Backup;
    
    %Convert W1 to vector
    W1 = W1(:);
    
    %Add h to a single element in W1 vector
    W1(i) = W1(i)+0.001;
    
    %Convert W back to matrix
    W1 = reshape(W1,[4,25]);
    
    %Calculate current loss
    score = W1*x1;
    currentLoss = hinge_loss(score, 1);
    
    %Compute new gradient derivative
    newDerivative = (currentLoss - previousLoss)/0.001;
    
    %Update output vector
    output2(i) = newDerivative;
    
    %Update current/previous
    previousLoss = currentLoss;
    
    i = i + 1; %repeat until i > 100
    
end

display("Outputting resultant gradient derivative vector with learning rate h=0.001:");
display(output2);

%End of File