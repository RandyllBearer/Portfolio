%Randyll Bearer:    HW8P:   Cross_Entropy_Loss()
function [loss] = cross_entropy_loss(scores, correct_class)
%scores is a 4x1 set of predicted scores, one score per class for a sample
%4x1 = [0; 0; 0; 0]
%correct_class is the correct class for that same sample

%Compute numerator
groundTruthScore = scores(correct_class);
numerator = exp(groundTruthScore);

%Compute denominator individual sums
if correct_class == 1
    denominator1 = exp(scores(2));
    denominator2 = exp(scores(3));
    denominator3 = exp(scores(4));
    
elseif correct_class == 2
    denominator1 = exp(scores(1));
    denominator2 = exp(scores(3));
    denominator3 = exp(scores(4));
    
elseif correct_class == 3
    denominator1 = exp(scores(1));
    denominator2 = exp(scores(2));
    denominator3 = exp(scores(4));
    
elseif correct_class == 4
    denominator1 = exp(scores(1));
    denominator2 = exp(scores(2));
    denominator3 = exp(scores(3));
    
end

%Compute denominator summation
denominator = denominator1 + denominator2 + denominator3;

%Compute total Probability
probability = numerator / denominator;

%Apply inverse log
loss = -log10(probability); %Piazza discussion makes log10 seem best choice

end
%End of File