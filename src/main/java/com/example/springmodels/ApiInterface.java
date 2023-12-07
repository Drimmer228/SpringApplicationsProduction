package com.example.springmodels;

import com.example.springmodels.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class ApiInterface {
    private final RestTemplate restTemplate;

    @Autowired
    public ApiInterface(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Iterable<AppCategoryModel> getAllCategoryModels(){
        ResponseEntity<Iterable<AppCategoryModel>> response = restTemplate.exchange(
                "http://localhost:8008/categories",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<AppCategoryModel>>() {}
        );

        Iterable<AppCategoryModel> categories = null;
        if (response.getStatusCode().is2xxSuccessful()) {
            categories = response.getBody();
        }
        return categories;
    }

    public RoleModel getRoleAtName(String roleName){
        ResponseEntity<RoleModel> response = restTemplate.getForEntity("http://localhost:8008/role/findAtRoleName/"+roleName, RoleModel.class);
        RoleModel roleModel = null;
        if (response.getStatusCode().is2xxSuccessful()){
            roleModel = response.getBody();
        }
        return roleModel;
    }

    public UserModel getUserAtId(Long id){
        ResponseEntity<UserModel> response = restTemplate.getForEntity("http://localhost:8008/user/"+id, UserModel.class);
        UserModel userModel = null;
        if (response.getStatusCode().is2xxSuccessful()){
            userModel = response.getBody();
        }
        return userModel;
    }

    public UserModel getUserAtUserName(String username){
        ResponseEntity<UserModel> response = restTemplate.getForEntity("http://localhost:8008/user/by-username/"+username, UserModel.class);
        UserModel userModel = null;
        if (response.getStatusCode().is2xxSuccessful()){
            userModel = response.getBody();
        }
        return userModel;
    }

    public ApplicationModel getApplicationAtId(Long id){
        ResponseEntity<ApplicationModel> response = restTemplate.getForEntity("http://localhost:8008/application/"+id, ApplicationModel.class);
        ApplicationModel applicationModel = null;
        if (response.getStatusCode().is2xxSuccessful()){
            applicationModel = response.getBody();
        }
        return applicationModel;
    }

    public ApplicationStatusModel getStatusAtName(String statusName){
        ResponseEntity<ApplicationStatusModel> response = restTemplate.getForEntity("http://localhost:8008/status/"+statusName, ApplicationStatusModel.class);
        ApplicationStatusModel applicationStatusModel = null;
        if (response.getStatusCode().is2xxSuccessful()){
            applicationStatusModel = response.getBody();
        }
        return applicationStatusModel;
    }

    public void saveApplication(ApplicationModel applicationModel) {
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8008/application/save",
                HttpMethod.POST,
                new HttpEntity<>(applicationModel),
                String.class);
    }

    public void saveUser(UserModel userModel) {
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8008/user/save",
                HttpMethod.POST,
                new HttpEntity<>(userModel),
                String.class);
    }

    public void saveNotification(NotificationsModel notificationsModel) {
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:8008/notify/save",
                HttpMethod.POST,
                new HttpEntity<>(notificationsModel),
                String.class);
    }

    public List<String> findDistinctPublisherIds() {
        ResponseEntity<List<String>> response = restTemplate.exchange(
                "http://localhost:8008/application/uniqueNames",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {});
        List<String> names = null;
        if (response.getStatusCode().is2xxSuccessful()){
            names = response.getBody();
        }
        return names;
    }

    public List<ApplicationModel> findAllByPublisherRoleRoleName(String roleName) {
        ResponseEntity<List<ApplicationModel>> response = restTemplate.exchange(
                "http://localhost:8008/application/findByPublisherRole/" + roleName,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ApplicationModel>>() {});
        List<ApplicationModel> applicationModel = null;
        if (response.getStatusCode().is2xxSuccessful()){
            applicationModel = response.getBody();
        }
        return applicationModel;
    }


    public List<NotificationsModel> findAllByUserId(Long id) {
        ResponseEntity<List<NotificationsModel>> response = restTemplate.exchange(
                "http://localhost:8008/notify/findByUserId/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NotificationsModel>>() {});
        List<NotificationsModel> notificationsModels = null;
        if (response.getStatusCode().is2xxSuccessful()){
            notificationsModels = response.getBody();
        }
        return notificationsModels;
    }

    public Optional<NotificationsModel> findNotifyById(Long id) {
        ResponseEntity<Optional<NotificationsModel>> response = restTemplate.exchange(
                "http://localhost:8008/notify/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Optional<NotificationsModel>>() {});
        Optional<NotificationsModel> notificationsModel = null;
        if (response.getStatusCode().is2xxSuccessful()){
            notificationsModel = response.getBody();
        }
        return notificationsModel;
    }

    public void deleteAllNotifyByUserId(Long id) {
        ResponseEntity response = restTemplate.exchange(
                "http://localhost:8008/notify/deleteByUserId/"+id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<String>() {});
        Object answer = null;
        if (response.getStatusCode().is2xxSuccessful()){
            answer = response.getBody();
        }
    }

    public UserModel findByRole(RoleModel roleModel) {
        ResponseEntity<UserModel> response = restTemplate.exchange(
                "http://localhost:8008/user/findByRole/" + roleModel.getId(),
                HttpMethod.GET,
                null,
                UserModel.class);
        UserModel userModel = null;
        if (response.getStatusCode().is2xxSuccessful()){
            userModel = response.getBody();
        }
        return userModel;
    }

    public List<SalesStatisticsModel> findAllByApplicationId(Long id) {
        ResponseEntity<List<SalesStatisticsModel>> response = restTemplate.exchange(
                "http://localhost:8008/statistic/findByApplicationId/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SalesStatisticsModel>>() {});
        List<SalesStatisticsModel> salesStatisticsModelList = null;
        if (response.getStatusCode().is2xxSuccessful()){
            salesStatisticsModelList = response.getBody();
        }
        return salesStatisticsModelList;
    }

    public List<ApplicationModel> findAllByPublisherId(Long id) {
        ResponseEntity<List<ApplicationModel>> response = restTemplate.exchange(
                "http://localhost:8008/application/findByPublisherId/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ApplicationModel>>() {});
        List<ApplicationModel> applicationModelList = null;
        if (response.getStatusCode().is2xxSuccessful()){
            applicationModelList = response.getBody();
        }
        return applicationModelList;
    }

    public List<SalesStatisticsModel> findAllStatistic() {
        ResponseEntity<List<SalesStatisticsModel>> response = restTemplate.exchange(
                "http://localhost:8008/statistic/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SalesStatisticsModel>>() {});
        List<SalesStatisticsModel> statisticsModels = null;
        if (response.getStatusCode().is2xxSuccessful()){
            statisticsModels = response.getBody();
        }
        return statisticsModels;
    }
}
