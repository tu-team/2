using System;
using System.Collections.Generic;
using System.Linq;
using AutoMapper;
using TU.Application.Dto;
using TU.Domain;
using TU.Domain.Queries;
using TU.Domain.Queries.Criterions;
using TU.Infrastructure.ExternalService;
using TU.Infrastructure.Utilities;

namespace TU.Application
{
    internal class RequestService : IRequestService
    {
        private readonly IUnitOfWorkFactory _unitOfWorkFactory;
        private readonly IDateTimeService _dateTimeService;
        private readonly IStateManagerService _stateManagerService;
        private readonly ITuWebService _tuWebService;

        public RequestService(IUnitOfWorkFactory unitOfWorkFactory, IDateTimeService dateTimeService, IStateManagerService stateManagerService, ITuWebService tuWebService)
        {
            _unitOfWorkFactory = unitOfWorkFactory;
            _dateTimeService = dateTimeService;
            _stateManagerService = stateManagerService;
            _tuWebService = tuWebService;
        }

        public void CreateRequest(Request request)
        {
            if (request == null)
                throw new ArgumentNullException("request");

            using (IUnitOfWork unitOfWork = _unitOfWorkFactory.Create())
            {
                var requestEntity = unitOfWork.CreateEntity<Domain.Entities.Request>();

                string requestResult;

                // TODO: Add exception handling
                switch (request.Type)
                {
                    case RequestType.Operation:
                        //requestResult = _tuWebService.Operation(request.RequestString);
                        break;
                    case RequestType.Train:
                        //requestResult = _tuWebService.Operation(request.RequestString);
                        break;
                    default:
                        throw new UnsupportedRequestException(request.Type);
                }

                //var map = Mapper.Map<Request>();
                requestEntity.RequestDate = _dateTimeService.GetCurrentDateAndTime();
                requestEntity.RequestType = (Domain.Entities.RequestType) request.Type;
                //requestEntity.RequestText = request.RequestString;
                //requestEntity.RequestState = requestResult;
                //requestEntity.RequestId = ???;

                unitOfWork.Commit();
            }
        }

        public void SendMessage(Message message)
        {
            throw new NotImplementedException();
        }

        //public void SendRequestAsync(Request request)
        //{
        //    throw new NotImplementedException();
        //}

        public IEnumerable<Request> GetAllRequests()
        {
            using (IUnitOfWork unitOfWork = _unitOfWorkFactory.Create())
            {
                IEnumerable<Request> requests = unitOfWork
                    .BuildQuery()
                    .For<IEnumerable<Request>>()
                    .With(GetAllCriterion.Value);

                return requests;
            }
        }

        public Request GetRequestById(Guid id)
        {
            using (IUnitOfWork unitOfWork = _unitOfWorkFactory.Create())
            {
                Request request = unitOfWork
                    .BuildQuery()
                    .For<Request>()
                    .With(new GetByIdCriterion(id));

                return request;
            }
        }

        public Request GetRequestByRequestId(string requestId)
        {
            return null;
        }
    }
}