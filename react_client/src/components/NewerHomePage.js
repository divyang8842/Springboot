import React, {Component} from 'react';
import { Route, withRouter } from 'react-router-dom';
import * as API from '../api/API';
import Login from "./Login";
import Message from "./Message";
import Welcome from "./Welcome";
import SignUp from "./SignUp";
import UserProfile from "./UserProfile";
import logo from '../images/dropbox_logo740.png';
import UserActivity from  './UserActivity';
import ErrorBoundary from "./ErrorHandler";
import UserGroups from './UserGroup';

var link={
    marginRight:-100,
paddingTop:'-80',
outline: 'none',
background: 'none',
color: 'rgb(105, 11, 224)',
fontSize: '18px',
fontWeight: '800',
border: 'none',

}
var pqr={display:'inline-block',textAlign:'left',width:'50%'}

var list={listStyleType:'none',float:'left'}



class NewerHomePage extends Component {

    state = {
        isLoggedIn: false,
        message: '',
        username: '',
        filelist:[],
        root:'',
        userId:'',
        rootDir:''
    };

    componentWillMount(){
        if(localStorage.getItem("token")!=undefined && localStorage.getItem("token")!=0){
            this.setState({
                isLoggedIn:true
            });
        }
    }

    render() {
        return (
            <div className="mast-head__container container">

                <Route  path="/" render={() =>
                    <div>
                        <Login handleSubmit={this.handleSubmit}/>
                        <Message message={this.state.message}/>
                    </div>
                }/>


               {/* <Route exact path="/login" render={() => (
                    <div>
                        <Login handleSubmit={this.handleSubmit}/>
                        <Message message={this.state.message}/>
                    </div>
                )}/>*/}
                <Route  path="/welcome" render={() =>  ( (  this.state.isLoggedIn?(
                    <ErrorBoundary>
                    <Welcome goToPath={this.goToPath} signout={this.signout}  getToHome={this.getToHome}  data={this.state}/>
                    </ErrorBoundary>
                ):
                        (
                            <div>
                                <Login handleSubmit={this.handleSubmit}/>
                                <Message message={this.state.message}/>
                            </div>
                        )
                ))}/>

                <Route  path="/signUp" render={() => (
                    <div>
                    <SignUp handleSignUp={this.handleSignUp} />
                    <Message message={this.state.message}/>
                    </div>

                    )}/>

                <Route  path="/userprofile" render={() => ( (  this.state.isLoggedIn?(
                    <ErrorBoundary>
                        <UserProfile goToPath={this.goToPath} signout={this.signout} getToHome={this.getToHome} data={this.state} />
                    </ErrorBoundary>
                        ):
                        (
                            <div>
                                <Login handleSubmit={this.handleSubmit}/>
                                <Message message={this.state.message}/>
                            </div>
                        )
                ))}/>

                <Route  path="/usergroups" render={() => (this.state.isLoggedIn?(
                    <div>
                        <UserGroups goToPath={this.goToPath} signout={this.signout} getToHome={this.getToHome} data={this.state} />
                    </div>
                        ):
                        (
                            <div>
                                <Login handleSubmit={this.handleSubmit}/>
                                <Message message={this.state.message}/>
                            </div>
                        )

                )}/>

                <Route  path="/useractivity" render={() => (this.state.isLoggedIn?(
                    <ErrorBoundary>
                        <UserActivity goToPath={this.goToPath} signout={this.signout} getToHome={this.getToHome} data={this.state} />
                    </ErrorBoundary>
                        ):
                        (
                            <div>
                                <Login handleSubmit={this.handleSubmit}/>
                                <Message message={this.state.message}/>
                            </div>
                        )

                )}/>



               {/* <Route  path="*" render={() => ( <div>
                        <Login handleSubmit={this.handleSubmit}/>
                        <Message message={this.state.message}/>
                    </div>)}/>*/}

            </div>
        );
    }

    handleSubmit = (data) => {

        var userdata = {username:data.username,password:data.password};

        API.doLogin(userdata)
            .then((status) => {
            console.log(JSON.stringify(status));

                if (status[0]) {

                    localStorage.setItem("token", status[0].id);
                    localStorage.setItem("root", status[0].rootdir);
                    this.setState({
                        isLoggedIn: true,
                        message: "Welcome to Dropbox..!!",
                        username: status.username,
                       /* filelist:status.filelist,*/
                        root:status.rootdir,
                        userId:status.userid
                    });
                    this.props.history.push("/welcome");
                } else {
                    localStorage.setItem("token", null);
                    this.setState({
                        isLoggedIn: false,
                        message: "Wrong username or password. Try again..!!"
                    });
                }
            });
    };

    getToHome = () =>{
        this.props.history.push('/welcome');
    }

    goToPath = (path) =>{
        this.props.history.push(path);
    }



    signout = () =>{
        //
        API.doLogout({"data":""});

            localStorage.removeItem('token');
            localStorage.removeItem('root');
            //alert("done");
            this.props.history.push('/login');

    }
    handleSignUp = (userdata) => {
        API.doSignUp(userdata)
            .then((status) => {

                if (status == 201) {
                    this.setState({
                        isLoggedIn: false,
                        message: "Signup Successfull..!!",
                    });
                    this.props.history.push("/login");
                } else if (status === 401) {
                    this.setState({
                        isLoggedIn: false,
                        message: "SignUp Failed"
                    });
                }
            });
    };

}

export default withRouter(NewerHomePage);