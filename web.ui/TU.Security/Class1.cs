using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TU.Security
{
    public class AuthenticationService : IAuthenticationService
    {
        public bool DoLogin(LoginData loginData)
        {
            throw new NotImplementedException();
        }
    }

    public interface IAuthenticationService
    {
        bool DoLogin(LoginData loginData);
    }

    public class LoginData
    {
    }
}
