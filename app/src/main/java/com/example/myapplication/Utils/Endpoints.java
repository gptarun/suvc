package com.example.myapplication.Utils;

public class Endpoints {

  private static final String base_url = "http://suvcrste.com/";

  //admin endpoints
  public static final String get_all_teams = base_url+"app_getAllTeams.php";
  public static final String get_all_members = base_url+"app_getTeamMembers.php";
  public static final String update_team_score = base_url+"app_updateTeamScore.php";
  public static final String update_team_document_status = base_url+"app_updateStatusOfDocument.php";
  public static final String get_team_document_status = base_url+"app_getTeamDocumentStatus.php";
  public static final String get_team_details = base_url+"app_getTeamDetails.php";
  public static final String update_member_role = base_url+"app_updateRole.php";
  public static final String add_new_member = base_url+"app_addTeamMember.php";
  public static final String delete_member = base_url+"app_deleteMember.php";
  public static final String update_member = base_url+"app_updateMember.php";
  public static final String register_team = base_url+"app_registerTeam.php";
  public static final String update_team_status = base_url+"app_updateTeamStatus.php";
  public static final String delete_team = base_url+"app_deleteTeam.php";
}
