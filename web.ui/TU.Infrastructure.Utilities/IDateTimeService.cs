using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TU.Infrastructure.Utilities
{
    public interface IDateTimeService
    {
        DateTime GetCurrentDate();
        DateTime GetCurrentDateAndTime();
        DateTime GetCurrentDateUtc();
        DateTime GetCurrentDateAndTimeUtc();
    }
}
