using System;
using TU.Application.Dto;

namespace TU.Application
{
    public class UnsupportedRequestException : Exception
    {
        public RequestType RequestType { get; private set; }

        public UnsupportedRequestException(RequestType requestType)
        {
            RequestType = requestType;
        }
    }
}