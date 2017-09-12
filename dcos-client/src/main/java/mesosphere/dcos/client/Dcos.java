/*
* Copyright (c) 2017 HERE Europe B.V.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package mesosphere.dcos.client;

import java.util.List;
import feign.Param;
import feign.RequestLine;
import mesosphere.dcos.client.exception.DcosException;
import mesosphere.dcos.client.model.v2.App;
import mesosphere.dcos.client.model.v2.Auth;
import mesosphere.dcos.client.model.v2.DeleteAppTaskResponse;
import mesosphere.dcos.client.model.v2.DeleteAppTasksResponse;
import mesosphere.dcos.client.model.v2.Deployment;
import mesosphere.dcos.client.model.v2.GetAppResponse;
import mesosphere.dcos.client.model.v2.GetAppTasksResponse;
import mesosphere.dcos.client.model.v2.GetAppsResponse;
import mesosphere.dcos.client.model.v2.GetEventSubscriptionRegisterResponse;
import mesosphere.dcos.client.model.v2.GetEventSubscriptionsResponse;
import mesosphere.dcos.client.model.v2.GetServerInfoResponse;
import mesosphere.dcos.client.model.v2.GetTasksResponse;
import mesosphere.dcos.client.model.v2.Group;
import mesosphere.dcos.client.model.v2.QueueResponse;
import mesosphere.dcos.client.model.v2.Result;
import mesosphere.dcos.client.model.v2.Token;
import mesosphere.dcos.client.model.v2.UpdateAppResponse;

public interface Dcos {
	
	// Auth
	@RequestLine("POST /acs/api/v1/auth/login")
	Token getToken(Auth auth) throws DcosException;
	
    // Apps
	@RequestLine("GET /v2/apps")
	GetAppsResponse getApps() throws DcosException;

	@RequestLine("GET /v2/apps/{id}")
	GetAppResponse getApp(@Param("id") String id) throws DcosException;

	@RequestLine("GET /v2/apps/{id}?cmd={cmd}&embed={embed}")
	GetAppResponse getApp(@Param("id") String id, @Param("cmd") String cmd, @Param("embed") List<String> embed) throws DcosException;

	@RequestLine("GET /v2/apps/{id}/tasks")
	GetAppTasksResponse getAppTasks(@Param("id") String id);

	@RequestLine("GET /v2/tasks")
	GetTasksResponse getTasks() throws DcosException;

	@RequestLine("POST /v2/apps")
	App createApp(App app) throws DcosException;

	@RequestLine("PUT /v2/apps/{app_id}?force={force}")
	UpdateAppResponse updateApp(@Param("app_id") String appId, @Param("force") boolean force, App app) throws DcosException;

	@RequestLine("POST /v2/apps/{id}/restart?force={force}")
	void restartApp(@Param("id") String id, @Param("force") boolean force) throws DcosException;

	@RequestLine("DELETE /v2/apps/{id}")
	Result deleteApp(@Param("id") String id) throws DcosException;

	@RequestLine("DELETE /v2/apps/{app_id}/tasks?host={host}&scale={scale}")
	DeleteAppTasksResponse deleteAppTasks(@Param("app_id") String appId, @Param("host") String host, @Param("scale") String scale) throws DcosException;

	@RequestLine("DELETE /v2/apps/{app_id}/tasks/{task_id}?scale={scale}")
	DeleteAppTaskResponse deleteAppTask(@Param("app_id") String appId, @Param("task_id") String taskId, @Param("scale") String scale) throws DcosException;

    // Groups
	@RequestLine("POST /v2/groups")
	Result createGroup(Group group) throws DcosException;
	
	@RequestLine("DELETE /v2/groups/{id}")
	Result deleteGroup(@Param("id") String id) throws DcosException;
	
	@RequestLine("GET /v2/groups/{id}")
	Group getGroup(@Param("id") String id) throws DcosException;

    // Deployments
	@RequestLine("GET /v2/deployments")
	List<Deployment> getDeployments() throws DcosException;
	
	@RequestLine("DELETE /v2/deployments/{deploymentId}")
	void cancelDeploymentAndRollback(@Param("deploymentId") String id) throws DcosException;
	
	@RequestLine("DELETE /v2/deployments/{deploymentId}?force=true")
	void cancelDeployment(@Param("deploymentId") String id) throws DcosException;

    // Event Subscriptions
    @RequestLine("POST /v2/eventSubscriptions?callbackUrl={url}")
    public GetEventSubscriptionRegisterResponse register(@Param("url") String url) throws DcosException;

    @RequestLine("DELETE /v2/eventSubscriptions?callbackUrl={url}")
    public GetEventSubscriptionRegisterResponse unregister(@Param("url") String url) throws DcosException;

    @RequestLine("GET /v2/eventSubscriptions")
    public GetEventSubscriptionsResponse subscriptions() throws DcosException;

    // Queue
	@RequestLine("GET /v2/queue")
	QueueResponse getQueue() throws DcosException;

    // Server Info
    @RequestLine("GET /v2/info")
    GetServerInfoResponse getServerInfo() throws DcosException;
}
