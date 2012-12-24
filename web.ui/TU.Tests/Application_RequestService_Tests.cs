using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Moq;
using TU.Application;
using TU.Application.Dto;
using TU.Domain.Queries;
using TU.Infrastructure.Utilities;
using Xunit;

namespace TU.Tests
{
    public class Application_RequestService_Tests
    {
        private readonly MockRepository _mockRepository;
        private readonly AutoMockContainer _autoMockContainer;

        public Application_RequestService_Tests()
        {
            _mockRepository = new MockRepository(MockBehavior.Loose);
            _autoMockContainer = new AutoMockContainer(_mockRepository);
        }

        [Fact]
        public void SendRequest_Request_Null_Fails()
        {
            // Arrange
            _autoMockContainer
                .Register<IRequestService, RequestService>();

            var mockUnitOfWork = _autoMockContainer
                .GetMock<IUnitOfWork>();

            var requestEntity = new Domain.Entities.Request();

            mockUnitOfWork
                .Setup(uow => uow.CreateEntity<Domain.Entities.Request>())
                .Returns(requestEntity);

            _autoMockContainer
                .GetMock<IUnitOfWorkFactory>().Setup(uowf => uowf.Create())
                .Returns(mockUnitOfWork.Object);

            _autoMockContainer
                .GetMock<IDateTimeService>()
                .Setup(dts => dts.GetCurrentDateAndTime())
                .Returns(DateTime.Now);

            // Act
            Request request = null;

            Assert.ThrowsDelegate sendRequestAction = () => _autoMockContainer.Resolve<IRequestService>().CreateRequest(request);

            // Assert
            Assert.Throws<ArgumentNullException>(sendRequestAction);
        }

        [Fact]
        public void SendRequest_Request_NonEmpty_Passes()
        {
            // Arrange
            _autoMockContainer
                .Register<IRequestService, RequestService>();

            var mockUnitOfWork = _autoMockContainer
                .GetMock<IUnitOfWork>();

            var requestEntity = new Domain.Entities.Request();

            mockUnitOfWork
                .Setup(uow => uow.CreateEntity<Domain.Entities.Request>())
                .Returns(requestEntity);

            _autoMockContainer
                .GetMock<IUnitOfWorkFactory>().Setup(uowf => uowf.Create())
                .Returns(mockUnitOfWork.Object);

            _autoMockContainer
                .GetMock<IDateTimeService>()
                .Setup(dts => dts.GetCurrentDateAndTime())
                .Returns(DateTime.Now);

            // Act
            Request request = new Request
                                  {
                                      Type = RequestType.Operation,
                                      //RequestString = "Test operation"
                                  };

            Assert.ThrowsDelegate sendRequestAction = () => _autoMockContainer.Resolve<IRequestService>().CreateRequest(request);

            // Assert
            Assert.DoesNotThrow(sendRequestAction);
            //Assert.Equal(requestEntity.RequestText, request.RequestString);
            Assert.Equal(requestEntity.RequestType, (Domain.Entities.RequestType)request.Type);
        }
    }
}
