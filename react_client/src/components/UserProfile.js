import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import Button from 'react-bootstrap/lib/Button';
import Panel from 'react-bootstrap/lib/Panel';
import * as API from '../api/API';
import NavBar from "../components/Navbar";
import { FormControl, Checkbox } from 'react-bootstrap';

var fullscreen={position: 'fixed',
    top: '0',
    left: '0',
    bottom: '0',
    right:'0',
    overflow: 'auto',
    background: 'white'}
class SignUp extends  Component{
    state = {
        work: '',
        education: '',
        music:'',
        mobile:'',
        shows:'',
        sports:''

    };
    getHome =() =>{
        this.props.getToHome();
    }
    getUserProfile =()=>{
        this.props.goToPath('/userprofile');
    }
    getUserLogs =() =>{
        this.props.goToPath('/useractivity');
    }
    signout = () => {
        this.props.signout();
    }
    goToPath = (path) =>{
        this.props.goToPath(path);
    }
    componentWillMount(){
        API.getUserProfile({}).then((response) => {

            //alert("response is "+JSON.stringify(response));
            this.setState({
            work: response.work,
            education: response.education,
            music:response.music,
            mobile:response.mobile,
            shows:response.shows,
            sports:response.sports

        })
        });
    };
    setUserProfile = (userdata) => {
        API.setUserProfile(userdata)
            .then((status) => {
                if (status === 200) {
                    this.setState({
                        isLoggedIn: false,
                        message: "Data updated Successfully!!",
                    });
                   // this.props.history.push("/login");
                } else if (status === 401) {
                    this.setState({
                        isLoggedIn: false,
                        message: "Error while updarting user profile."
                    });
                }
            });
    };
    render(){
        var bgcolor = {backgroundColor:'dodgerblue'};
        var panelBg = {backgroundColor:'lightblue'};
        var color = {color:'blue'};
        return(
            <div style={fullscreen}>
                <NavBar goToPath = {this.goToPath} page = {"useractivity"} getUserLogs={this.getUserLogs} getUserProfile={this.getUserProfile}  getHome={this.getHome} shareFile={this.getHome}   signout= {this.signout}></NavBar>
                <div className="col-md-8 col-md-8">
                <div className="text-center">
                    <h1 className="login-brand-text">User Profile</h1>
                </div>
                <Panel style={panelBg} header={<h3>User Profile</h3>} className="login-panel">
                    <form  >
                        <fieldset>
                            <div className="form-group" controlId="formHorizontalPassword">
                                Work  <FormControl className={`form-group `}
                                                   controlId="work"
                                                   placeholder="Work"
                                                   type="text"
                                                   value={this.state.work}
                                                   onChange={(event)=>{
                                                       this.setState({
                                                           work:event.target.value
                                                       });
                                                   }}
                                                   required
                                                   autoFocus
                            />
                            </div>
                            <div className="form-group" controlId="formHorizontalPassword">
                                Educarion  <FormControl className={`form-group `}
                                                        controlId="education"
                                                        placeholder="Eduction"
                                                        type="text"
                                                        value={this.state.education}
                                                        onChange={(event)=>{
                                                            this.setState({
                                                                education:event.target.value
                                                            });
                                                        }}
                                                        required
                                                        autoFocus
                            />
                            </div>
                            <div className="form-group" controlId="formHorizontalPassword">
                                Sprots   <FormControl className={`form-group `}
                                                      controlId="Sports"
                                                      placeholder="Sports"
                                                      type="text"
                                                      value={this.state.sports}
                                                      onChange={(event)=>{
                                                          this.setState({
                                                              sports:event.target.value
                                                          });
                                                      }}
                                                      required
                                                      autoFocus
                            />
                            </div>


                            <div className="form-group" controlId="formHorizontalPassword">
                                Music  <FormControl className={`form-group `}
                                                    controlId="music"
                                                    placeholder="Music"
                                                    type="text"
                                                    value={this.state.music}
                                                    onChange={(event)=>{
                                                        this.setState({
                                                            music:event.target.value
                                                        });
                                                    }}
                                                    required
                                                    autoFocus
                            />
                            </div>

                            <div className="form-group" controlId="formHorizontalPassword">
                                Mobile  <FormControl className={`form-group `}
                                                     controlId="mobile"
                                                     placeholder="Mobile"
                                                     type="text"
                                                     value={this.state.mobile}
                                                     onChange={(event)=>{
                                                         this.setState({
                                                             mobile:event.target.value
                                                         });
                                                     }}
                                                     required
                                                     autoFocus
                            />
                            </div>
                            <div className="form-group" controlId="formHorizontalPassword">
                                Shows  <FormControl className={`form-group `}
                                                    controlId="show"
                                                    placeholder="Show"
                                                    type="text"
                                                    value={this.state.shows}
                                                    onChange={(event)=>{
                                                        this.setState({
                                                            shows:event.target.value
                                                        });
                                                    }}
                                                    required
                                                    autoFocus
                            />
                            </div>
                            <Button style={bgcolor} onClick={() => this.setUserProfile(this.state)} bsSize="large" bsStyle="success" block>Update</Button>
                        </fieldset>
                    </form>
                </Panel>
            </div>
            </div>
        )
    }
}
export default withRouter(SignUp);