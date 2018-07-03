%ensures that the sum of each column in a matrix is equal to 1
function [B] = normalize_columns(A)
B = A./sum(A);

end
%only way I could think of to get it done in 1 line as required by the
%assignment. If you average everything element in a column, then each element
%will become a percentage. If you add all the percentages together, you
%will get 100% or 1. Admittedly this way has minor rounding errors 