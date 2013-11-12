x0 = 0; y0 = 0;
sigma = 0.3;
sigma_x = sigma;
sigma_y = sigma;
x_limit_pos = 1;
y_limit_pos = 1;
x_limit_neg = -1;
y_limit_neg = -1;
A = - 1/(sigma * sqrt(2 * pi));
 
# for theta = 0:pi/10:pi
    theta = pi/5;
    a = cos(theta)^2/2/sigma_x^2 + sin(theta)^2/2/sigma_y^2;
    b = -sin(2*theta)/4/sigma_x^2 + sin(2*theta)/4/sigma_y^2 ;
    c = sin(theta)^2/2/sigma_x^2 + cos(theta)^2/2/sigma_y^2;
 
    [X, Y] = meshgrid(x_limit_neg:.1:x_limit_pos, y_limit_neg:.1:y_limit_pos);
    Z = -exp( - (a*(X-x0).^2 + 2*b*(X-x0).*(Y-y0) + c*(Y-y0).^2)) + 1 ;
    surf(X,Y,Z);shading interp;view(-36,36);axis equal;drawnow
#end
