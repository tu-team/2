using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TU.Infrastructure.Configuration
{
    public interface IConfigurationProvider
    {
        string ConnectionString { get; set; }
    }
}
