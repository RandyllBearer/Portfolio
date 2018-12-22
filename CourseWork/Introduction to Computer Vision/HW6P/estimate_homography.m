%Randyll Bearer HW6P

%PA/PB = 4x2 matrices
%H = 3x3
function H = estimate_homography(PA, PB);

%Set up system of equations A (as shown in slide 30)
A = [];  %Store our results

for i = 1:4     %Loop through the four corresponding points
   xRegular = PA(i,1);  %Get coordinates of image1 points
   yRegular = PA(i,2);
   xPrime = PB(i,1);    %get coordinates of image2 points
   yPrime = PB(i,2);
   %Form of 'A' Taken from slide 30
   tempSystem = [-xRegular, -yRegular, -1, 0, 0, 0, xRegular*xPrime, yRegular*yPrime, xPrime;
        0, 0, 0, -xRegular, -yRegular, -1, xRegular*yPrime, yRegular*yPrime, yPrime];
   A = [A; tempSystem]; %Increment return value
    
end

%Solve for H (as provided in assignment)
[~, ~, V] = svd(A);
h = V(:, end);
H = reshape(h, 3, 3)';

end
%End of File