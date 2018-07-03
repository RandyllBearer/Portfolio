%Take in a matrix M and remove all duplicate rows from M and output N
function [N] = my_unique(M)
numRows = size(M,1);
numColumns = size(M,2);
i = 0;
j = 1;
depth = 1;

while (i) <= numRows    %everytime we begin to check a new base row
   i = i + 1;
   j = i + 1;

   while (j) <= numRows
      
       while (depth) <= numColumns
            if M(i,depth) == M(j, depth) %if i,j == i+1, j
                if (depth) == numColumns    %if every element has been ==
                    M(j,:) = []; %delete this row
                    numRows = numRows - 1;
                    j = i + 1;
                    depth = 1;
                    break;
                end
             else
                depth = 1;
                j = j + 1;
                break;
                
            end
            depth = depth + 1;
             
       end
           
   end
   
end

N = M;

end

%Basic idea, look at row I
%then look at row i + j
    %if duplicate, delete the row [ M(rowToDelete,:) = [] ]
    %if different, increment j
%if j > numRows, increment i and reset j to i+1
%if i == numRows, nothing left to check
