# G(x) = - 1/(sigma*sqrt(2*pi))exp((-x^2)/(2*sigma^2))
sigma = 0.3;
x_limit_pos = 1;
x_limit_neg = -1;
A = -1/(sigma * sqrt(2 * pi));
X = [x_limit_neg:0.1:x_limit_pos];
Y= -exp((-X.^2)/(2*sigma^2)) + 1;
plot(X,Y);
shading interp;
axis equal;
drawnow
