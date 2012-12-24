using System;

namespace TU.Infrastructure.Utilities
{
    internal class DateTimeService : IDateTimeService
    {
        public DateTime GetCurrentDate()
        {
            return DateTime.Now.Date;
        }

        public DateTime GetCurrentDateAndTime()
        {
            return DateTime.Now;
        }

        public DateTime GetCurrentDateUtc()
        {
            return DateTime.UtcNow.Date;
        }

        public DateTime GetCurrentDateAndTimeUtc()
        {
            return DateTime.UtcNow;
        }
    }
}