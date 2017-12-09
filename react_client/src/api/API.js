/*import axios from 'axios';*/

const api = process.env.REACT_APP_CONTACTS_API_URL || 'http://localhost:8080'

const headers = {
    'Accept': 'application/json'
};

export const doLogin = (payload) =>
   // alert();
   // alert('payload : '+JSON.stringify(payload));
    fetch(`${api}/user/login`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => res.json())
        .then(res =>{
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });
export const doSignUp = (payload) =>
    fetch(`${api}/user/add`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload),
        credentials:'include'
    }).then(res => {
        return res.status;
    })
        .catch(error => {
            console.log("This is error");
            return error;
        });

export const doUpload = (payload) =>
    fetch(`${api}/files/upload`, {
        method: 'POST',

        body: payload,
        credentials:'include'

    }).then(res => res.json())
        .then(res =>{
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });

export const doMkdir = (payload) =>

    fetch(`${api}/directory/mkdir`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload),
        credentials:'include'
    }).then(res => {
        return res.status;
    })
        .catch(error => {
            console.log("This is error");
            return error;
        });

export const deleteDir = (payload) =>
    fetch(`${api}/directory/delDir`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload),
        credentials:'include'
    }).then(res => {
        return res.status;
    })
        .catch(error => {
            console.log("This is error");
            return error;
        });


export const getChildDirs =(payload) =>

    fetch(`${api}/directory/getChildDir`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => res.json())
        .then(res =>{
           // debugger;
            console.log(res);
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });


export const doShareFile = (payload) =>
    fetch(`${api}/permission/add`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => res.json())
        .then(res =>{
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });

export const doLogout = (payload) =>
    fetch(`${api}/user/logout`, {
        method: 'POST',
        headers: {
            ...headers,
        },
        credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => {
            localStorage.removeItem("token");
            localStorage.removeItem("root");
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });


export const doDownload = (payload) =>
    fetch(`${api}/directory/download`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        credentials:'include',
        body: JSON.stringify(payload)
    })

        .then(res =>{
            return res;
        })
        .catch(function (error) {
            console.log(error);
        });


export const doStar = (payload) =>
    fetch(`${api}/star/star`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => res.json())
        .then(res =>{
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });
export const doUnStar = (payload) =>
    fetch(`${api}/unstar`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => res.json())
        .then(res =>{
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });

export const getUserLogs = (payload) =>
    fetch(`${api}/directory/getUserLogs`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        credentials:'include',
        body: JSON.stringify(payload)
    }).then(res => res.json())
        .then(res =>{
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });

export const setUserProfile = (payload) =>
    fetch(`${api}/profile/add`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload),
        credentials:'include'
    }).then(res => {
        return res.status;
    })
        .catch(error => {
            console.log("This is error");
            return error;
        });


export const getUserProfile = (payload) =>
    fetch(`${api}/profile/get`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload),
        credentials:'include'
    }).then(res => res.json())
        .then(res =>{
            //alert("res is "+JSON.stringify(res))
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });

export const validateEmails = (payload) =>
    fetch(`${api}/validateEmails`, {
        method: 'POST',
        headers: {
            ...headers,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload),
        credentials:'include'
    }).then(res => res.json())
        .then(res =>{
            return res;
        })
        .catch(error => {
            console.log("This is error");
            return error;
        });
 export const getUserGrpups = (payload) =>
     fetch(`${api}/getUserGroups`, {
         method: 'POST',
         headers: {
             ...headers,
             'Content-Type': 'application/json'
         },
         body: JSON.stringify(payload),
         credentials:'include'
     }).then(res => res.json())
         .then(res =>{
             return res;
         })
         .catch(error => {
             console.log("This is error");
             return error;
         });

 export const setUserGroup = (payload) =>
     fetch(`${api}/setUserGroups`, {
         method: 'POST',
         headers: {
             ...headers,
             'Content-Type': 'application/json'
         },
         body: JSON.stringify(payload),
         credentials:'include'
     }).then(res => res.json())
         .then(res =>{
             return res;
         })
         .catch(error => {
             console.log("This is error");
             return error;
         });