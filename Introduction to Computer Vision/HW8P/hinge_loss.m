%Randyll Bearer:    HW8P:   Hinge_Loss()
function [loss] = hinge_loss(scores, correct_class)
%scores is a 4x1 set of predicted scores, one score per class for a sample
%4x1 = [0; 0; 0; 0]
%correct_class is the correct class for that same sample

if correct_class == 1
    score1 = max(0, (scores(2)-scores(1)+1));
    score2 = max(0, (scores(3)-scores(1)+1));
    score3 = max(0, (scores(4)-scores(1)+1));
    
elseif correct_class == 2
    score1 = max(0, (scores(1)-scores(2)+1));
    score2 = max(0, (scores(3)-scores(2)+1));
    score3 = max(0, (scores(4)-scores(2)+1));
    
elseif correct_class == 3
    score1 = max(0, (scores(1)-scores(3)+1));
    score2 = max(0, (scores(2)-scores(3)+1));
    score3 = max(0, (scores(4)-scores(3)+1));
    
elseif correct_class == 4
    score1 = max(0, (scores(1)-scores(4)+1));
    score2 = max(0, (scores(2)-scores(4)+1));
    score3 = max(0, (scores(3)-scores(4)+1));
    
end

loss = score1+score2+score3;

end
%End of File