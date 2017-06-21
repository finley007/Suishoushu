package com.changyi.fi.model;

import java.util.Date;

public class UserPO {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Host
     *
     * @mbggenerated
     */
    private String host;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.User
     *
     * @mbggenerated
     */
    private String user;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Select_priv
     *
     * @mbggenerated
     */
    private String select_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Insert_priv
     *
     * @mbggenerated
     */
    private String insert_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Update_priv
     *
     * @mbggenerated
     */
    private String update_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Delete_priv
     *
     * @mbggenerated
     */
    private String delete_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Create_priv
     *
     * @mbggenerated
     */
    private String create_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Drop_priv
     *
     * @mbggenerated
     */
    private String drop_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Reload_priv
     *
     * @mbggenerated
     */
    private String reload_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Shutdown_priv
     *
     * @mbggenerated
     */
    private String shutdown_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Process_priv
     *
     * @mbggenerated
     */
    private String process_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.File_priv
     *
     * @mbggenerated
     */
    private String file_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Grant_priv
     *
     * @mbggenerated
     */
    private String grant_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.References_priv
     *
     * @mbggenerated
     */
    private String references_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Index_priv
     *
     * @mbggenerated
     */
    private String index_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Alter_priv
     *
     * @mbggenerated
     */
    private String alter_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Show_db_priv
     *
     * @mbggenerated
     */
    private String show_db_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Super_priv
     *
     * @mbggenerated
     */
    private String super_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Create_tmp_table_priv
     *
     * @mbggenerated
     */
    private String create_tmp_table_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Lock_tables_priv
     *
     * @mbggenerated
     */
    private String lock_tables_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Execute_priv
     *
     * @mbggenerated
     */
    private String execute_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Repl_slave_priv
     *
     * @mbggenerated
     */
    private String repl_slave_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Repl_client_priv
     *
     * @mbggenerated
     */
    private String repl_client_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Create_view_priv
     *
     * @mbggenerated
     */
    private String create_view_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Show_view_priv
     *
     * @mbggenerated
     */
    private String show_view_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Create_routine_priv
     *
     * @mbggenerated
     */
    private String create_routine_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Alter_routine_priv
     *
     * @mbggenerated
     */
    private String alter_routine_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Create_user_priv
     *
     * @mbggenerated
     */
    private String create_user_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Event_priv
     *
     * @mbggenerated
     */
    private String event_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Trigger_priv
     *
     * @mbggenerated
     */
    private String trigger_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.Create_tablespace_priv
     *
     * @mbggenerated
     */
    private String create_tablespace_priv;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.ssl_type
     *
     * @mbggenerated
     */
    private String ssl_type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.max_questions
     *
     * @mbggenerated
     */
    private Integer max_questions;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.max_updates
     *
     * @mbggenerated
     */
    private Integer max_updates;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.max_connections
     *
     * @mbggenerated
     */
    private Integer max_connections;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.max_user_connections
     *
     * @mbggenerated
     */
    private Integer max_user_connections;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.plugin
     *
     * @mbggenerated
     */
    private String plugin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password_expired
     *
     * @mbggenerated
     */
    private String password_expired;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password_last_changed
     *
     * @mbggenerated
     */
    private Date password_last_changed;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.password_lifetime
     *
     * @mbggenerated
     */
    private Short password_lifetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.account_locked
     *
     * @mbggenerated
     */
    private String account_locked;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Host
     *
     * @return the value of user.Host
     *
     * @mbggenerated
     */
    public String getHost() {
        return host;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Host
     *
     * @param host the value for user.Host
     *
     * @mbggenerated
     */
    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.User
     *
     * @return the value of user.User
     *
     * @mbggenerated
     */
    public String getUser() {
        return user;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.User
     *
     * @param user the value for user.User
     *
     * @mbggenerated
     */
    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Select_priv
     *
     * @return the value of user.Select_priv
     *
     * @mbggenerated
     */
    public String getSelect_priv() {
        return select_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Select_priv
     *
     * @param select_priv the value for user.Select_priv
     *
     * @mbggenerated
     */
    public void setSelect_priv(String select_priv) {
        this.select_priv = select_priv == null ? null : select_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Insert_priv
     *
     * @return the value of user.Insert_priv
     *
     * @mbggenerated
     */
    public String getInsert_priv() {
        return insert_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Insert_priv
     *
     * @param insert_priv the value for user.Insert_priv
     *
     * @mbggenerated
     */
    public void setInsert_priv(String insert_priv) {
        this.insert_priv = insert_priv == null ? null : insert_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Update_priv
     *
     * @return the value of user.Update_priv
     *
     * @mbggenerated
     */
    public String getUpdate_priv() {
        return update_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Update_priv
     *
     * @param update_priv the value for user.Update_priv
     *
     * @mbggenerated
     */
    public void setUpdate_priv(String update_priv) {
        this.update_priv = update_priv == null ? null : update_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Delete_priv
     *
     * @return the value of user.Delete_priv
     *
     * @mbggenerated
     */
    public String getDelete_priv() {
        return delete_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Delete_priv
     *
     * @param delete_priv the value for user.Delete_priv
     *
     * @mbggenerated
     */
    public void setDelete_priv(String delete_priv) {
        this.delete_priv = delete_priv == null ? null : delete_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Create_priv
     *
     * @return the value of user.Create_priv
     *
     * @mbggenerated
     */
    public String getCreate_priv() {
        return create_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Create_priv
     *
     * @param create_priv the value for user.Create_priv
     *
     * @mbggenerated
     */
    public void setCreate_priv(String create_priv) {
        this.create_priv = create_priv == null ? null : create_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Drop_priv
     *
     * @return the value of user.Drop_priv
     *
     * @mbggenerated
     */
    public String getDrop_priv() {
        return drop_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Drop_priv
     *
     * @param drop_priv the value for user.Drop_priv
     *
     * @mbggenerated
     */
    public void setDrop_priv(String drop_priv) {
        this.drop_priv = drop_priv == null ? null : drop_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Reload_priv
     *
     * @return the value of user.Reload_priv
     *
     * @mbggenerated
     */
    public String getReload_priv() {
        return reload_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Reload_priv
     *
     * @param reload_priv the value for user.Reload_priv
     *
     * @mbggenerated
     */
    public void setReload_priv(String reload_priv) {
        this.reload_priv = reload_priv == null ? null : reload_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Shutdown_priv
     *
     * @return the value of user.Shutdown_priv
     *
     * @mbggenerated
     */
    public String getShutdown_priv() {
        return shutdown_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Shutdown_priv
     *
     * @param shutdown_priv the value for user.Shutdown_priv
     *
     * @mbggenerated
     */
    public void setShutdown_priv(String shutdown_priv) {
        this.shutdown_priv = shutdown_priv == null ? null : shutdown_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Process_priv
     *
     * @return the value of user.Process_priv
     *
     * @mbggenerated
     */
    public String getProcess_priv() {
        return process_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Process_priv
     *
     * @param process_priv the value for user.Process_priv
     *
     * @mbggenerated
     */
    public void setProcess_priv(String process_priv) {
        this.process_priv = process_priv == null ? null : process_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.File_priv
     *
     * @return the value of user.File_priv
     *
     * @mbggenerated
     */
    public String getFile_priv() {
        return file_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.File_priv
     *
     * @param file_priv the value for user.File_priv
     *
     * @mbggenerated
     */
    public void setFile_priv(String file_priv) {
        this.file_priv = file_priv == null ? null : file_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Grant_priv
     *
     * @return the value of user.Grant_priv
     *
     * @mbggenerated
     */
    public String getGrant_priv() {
        return grant_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Grant_priv
     *
     * @param grant_priv the value for user.Grant_priv
     *
     * @mbggenerated
     */
    public void setGrant_priv(String grant_priv) {
        this.grant_priv = grant_priv == null ? null : grant_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.References_priv
     *
     * @return the value of user.References_priv
     *
     * @mbggenerated
     */
    public String getReferences_priv() {
        return references_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.References_priv
     *
     * @param references_priv the value for user.References_priv
     *
     * @mbggenerated
     */
    public void setReferences_priv(String references_priv) {
        this.references_priv = references_priv == null ? null : references_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Index_priv
     *
     * @return the value of user.Index_priv
     *
     * @mbggenerated
     */
    public String getIndex_priv() {
        return index_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Index_priv
     *
     * @param index_priv the value for user.Index_priv
     *
     * @mbggenerated
     */
    public void setIndex_priv(String index_priv) {
        this.index_priv = index_priv == null ? null : index_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Alter_priv
     *
     * @return the value of user.Alter_priv
     *
     * @mbggenerated
     */
    public String getAlter_priv() {
        return alter_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Alter_priv
     *
     * @param alter_priv the value for user.Alter_priv
     *
     * @mbggenerated
     */
    public void setAlter_priv(String alter_priv) {
        this.alter_priv = alter_priv == null ? null : alter_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Show_db_priv
     *
     * @return the value of user.Show_db_priv
     *
     * @mbggenerated
     */
    public String getShow_db_priv() {
        return show_db_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Show_db_priv
     *
     * @param show_db_priv the value for user.Show_db_priv
     *
     * @mbggenerated
     */
    public void setShow_db_priv(String show_db_priv) {
        this.show_db_priv = show_db_priv == null ? null : show_db_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Super_priv
     *
     * @return the value of user.Super_priv
     *
     * @mbggenerated
     */
    public String getSuper_priv() {
        return super_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Super_priv
     *
     * @param super_priv the value for user.Super_priv
     *
     * @mbggenerated
     */
    public void setSuper_priv(String super_priv) {
        this.super_priv = super_priv == null ? null : super_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Create_tmp_table_priv
     *
     * @return the value of user.Create_tmp_table_priv
     *
     * @mbggenerated
     */
    public String getCreate_tmp_table_priv() {
        return create_tmp_table_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Create_tmp_table_priv
     *
     * @param create_tmp_table_priv the value for user.Create_tmp_table_priv
     *
     * @mbggenerated
     */
    public void setCreate_tmp_table_priv(String create_tmp_table_priv) {
        this.create_tmp_table_priv = create_tmp_table_priv == null ? null : create_tmp_table_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Lock_tables_priv
     *
     * @return the value of user.Lock_tables_priv
     *
     * @mbggenerated
     */
    public String getLock_tables_priv() {
        return lock_tables_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Lock_tables_priv
     *
     * @param lock_tables_priv the value for user.Lock_tables_priv
     *
     * @mbggenerated
     */
    public void setLock_tables_priv(String lock_tables_priv) {
        this.lock_tables_priv = lock_tables_priv == null ? null : lock_tables_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Execute_priv
     *
     * @return the value of user.Execute_priv
     *
     * @mbggenerated
     */
    public String getExecute_priv() {
        return execute_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Execute_priv
     *
     * @param execute_priv the value for user.Execute_priv
     *
     * @mbggenerated
     */
    public void setExecute_priv(String execute_priv) {
        this.execute_priv = execute_priv == null ? null : execute_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Repl_slave_priv
     *
     * @return the value of user.Repl_slave_priv
     *
     * @mbggenerated
     */
    public String getRepl_slave_priv() {
        return repl_slave_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Repl_slave_priv
     *
     * @param repl_slave_priv the value for user.Repl_slave_priv
     *
     * @mbggenerated
     */
    public void setRepl_slave_priv(String repl_slave_priv) {
        this.repl_slave_priv = repl_slave_priv == null ? null : repl_slave_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Repl_client_priv
     *
     * @return the value of user.Repl_client_priv
     *
     * @mbggenerated
     */
    public String getRepl_client_priv() {
        return repl_client_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Repl_client_priv
     *
     * @param repl_client_priv the value for user.Repl_client_priv
     *
     * @mbggenerated
     */
    public void setRepl_client_priv(String repl_client_priv) {
        this.repl_client_priv = repl_client_priv == null ? null : repl_client_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Create_view_priv
     *
     * @return the value of user.Create_view_priv
     *
     * @mbggenerated
     */
    public String getCreate_view_priv() {
        return create_view_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Create_view_priv
     *
     * @param create_view_priv the value for user.Create_view_priv
     *
     * @mbggenerated
     */
    public void setCreate_view_priv(String create_view_priv) {
        this.create_view_priv = create_view_priv == null ? null : create_view_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Show_view_priv
     *
     * @return the value of user.Show_view_priv
     *
     * @mbggenerated
     */
    public String getShow_view_priv() {
        return show_view_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Show_view_priv
     *
     * @param show_view_priv the value for user.Show_view_priv
     *
     * @mbggenerated
     */
    public void setShow_view_priv(String show_view_priv) {
        this.show_view_priv = show_view_priv == null ? null : show_view_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Create_routine_priv
     *
     * @return the value of user.Create_routine_priv
     *
     * @mbggenerated
     */
    public String getCreate_routine_priv() {
        return create_routine_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Create_routine_priv
     *
     * @param create_routine_priv the value for user.Create_routine_priv
     *
     * @mbggenerated
     */
    public void setCreate_routine_priv(String create_routine_priv) {
        this.create_routine_priv = create_routine_priv == null ? null : create_routine_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Alter_routine_priv
     *
     * @return the value of user.Alter_routine_priv
     *
     * @mbggenerated
     */
    public String getAlter_routine_priv() {
        return alter_routine_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Alter_routine_priv
     *
     * @param alter_routine_priv the value for user.Alter_routine_priv
     *
     * @mbggenerated
     */
    public void setAlter_routine_priv(String alter_routine_priv) {
        this.alter_routine_priv = alter_routine_priv == null ? null : alter_routine_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Create_user_priv
     *
     * @return the value of user.Create_user_priv
     *
     * @mbggenerated
     */
    public String getCreate_user_priv() {
        return create_user_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Create_user_priv
     *
     * @param create_user_priv the value for user.Create_user_priv
     *
     * @mbggenerated
     */
    public void setCreate_user_priv(String create_user_priv) {
        this.create_user_priv = create_user_priv == null ? null : create_user_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Event_priv
     *
     * @return the value of user.Event_priv
     *
     * @mbggenerated
     */
    public String getEvent_priv() {
        return event_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Event_priv
     *
     * @param event_priv the value for user.Event_priv
     *
     * @mbggenerated
     */
    public void setEvent_priv(String event_priv) {
        this.event_priv = event_priv == null ? null : event_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Trigger_priv
     *
     * @return the value of user.Trigger_priv
     *
     * @mbggenerated
     */
    public String getTrigger_priv() {
        return trigger_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Trigger_priv
     *
     * @param trigger_priv the value for user.Trigger_priv
     *
     * @mbggenerated
     */
    public void setTrigger_priv(String trigger_priv) {
        this.trigger_priv = trigger_priv == null ? null : trigger_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.Create_tablespace_priv
     *
     * @return the value of user.Create_tablespace_priv
     *
     * @mbggenerated
     */
    public String getCreate_tablespace_priv() {
        return create_tablespace_priv;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.Create_tablespace_priv
     *
     * @param create_tablespace_priv the value for user.Create_tablespace_priv
     *
     * @mbggenerated
     */
    public void setCreate_tablespace_priv(String create_tablespace_priv) {
        this.create_tablespace_priv = create_tablespace_priv == null ? null : create_tablespace_priv.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.ssl_type
     *
     * @return the value of user.ssl_type
     *
     * @mbggenerated
     */
    public String getSsl_type() {
        return ssl_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.ssl_type
     *
     * @param ssl_type the value for user.ssl_type
     *
     * @mbggenerated
     */
    public void setSsl_type(String ssl_type) {
        this.ssl_type = ssl_type == null ? null : ssl_type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.max_questions
     *
     * @return the value of user.max_questions
     *
     * @mbggenerated
     */
    public Integer getMax_questions() {
        return max_questions;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.max_questions
     *
     * @param max_questions the value for user.max_questions
     *
     * @mbggenerated
     */
    public void setMax_questions(Integer max_questions) {
        this.max_questions = max_questions;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.max_updates
     *
     * @return the value of user.max_updates
     *
     * @mbggenerated
     */
    public Integer getMax_updates() {
        return max_updates;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.max_updates
     *
     * @param max_updates the value for user.max_updates
     *
     * @mbggenerated
     */
    public void setMax_updates(Integer max_updates) {
        this.max_updates = max_updates;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.max_connections
     *
     * @return the value of user.max_connections
     *
     * @mbggenerated
     */
    public Integer getMax_connections() {
        return max_connections;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.max_connections
     *
     * @param max_connections the value for user.max_connections
     *
     * @mbggenerated
     */
    public void setMax_connections(Integer max_connections) {
        this.max_connections = max_connections;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.max_user_connections
     *
     * @return the value of user.max_user_connections
     *
     * @mbggenerated
     */
    public Integer getMax_user_connections() {
        return max_user_connections;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.max_user_connections
     *
     * @param max_user_connections the value for user.max_user_connections
     *
     * @mbggenerated
     */
    public void setMax_user_connections(Integer max_user_connections) {
        this.max_user_connections = max_user_connections;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.plugin
     *
     * @return the value of user.plugin
     *
     * @mbggenerated
     */
    public String getPlugin() {
        return plugin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.plugin
     *
     * @param plugin the value for user.plugin
     *
     * @mbggenerated
     */
    public void setPlugin(String plugin) {
        this.plugin = plugin == null ? null : plugin.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password_expired
     *
     * @return the value of user.password_expired
     *
     * @mbggenerated
     */
    public String getPassword_expired() {
        return password_expired;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password_expired
     *
     * @param password_expired the value for user.password_expired
     *
     * @mbggenerated
     */
    public void setPassword_expired(String password_expired) {
        this.password_expired = password_expired == null ? null : password_expired.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password_last_changed
     *
     * @return the value of user.password_last_changed
     *
     * @mbggenerated
     */
    public Date getPassword_last_changed() {
        return password_last_changed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password_last_changed
     *
     * @param password_last_changed the value for user.password_last_changed
     *
     * @mbggenerated
     */
    public void setPassword_last_changed(Date password_last_changed) {
        this.password_last_changed = password_last_changed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.password_lifetime
     *
     * @return the value of user.password_lifetime
     *
     * @mbggenerated
     */
    public Short getPassword_lifetime() {
        return password_lifetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.password_lifetime
     *
     * @param password_lifetime the value for user.password_lifetime
     *
     * @mbggenerated
     */
    public void setPassword_lifetime(Short password_lifetime) {
        this.password_lifetime = password_lifetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.account_locked
     *
     * @return the value of user.account_locked
     *
     * @mbggenerated
     */
    public String getAccount_locked() {
        return account_locked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.account_locked
     *
     * @param account_locked the value for user.account_locked
     *
     * @mbggenerated
     */
    public void setAccount_locked(String account_locked) {
        this.account_locked = account_locked == null ? null : account_locked.trim();
    }
}