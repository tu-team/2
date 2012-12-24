using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TU.Infrastructure.ExternalService
{
    public interface ITuWebService
    {
        string Train(string request);
        string Operation(string request);
    }

    public class StubTuWebService : ITuWebService
    {
        public string Train(string request)
        {
            return "";
        }

        public string Operation(string request)
        {
            return "";
        }
    }
}
