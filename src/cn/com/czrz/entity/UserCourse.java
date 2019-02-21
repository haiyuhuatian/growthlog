
package cn.com.czrz.entity;

import java.math.BigDecimal;

import cn.com.gwypx.jdbc.annotation.Id;
import cn.com.gwypx.jdbc.annotation.Table;

@Table(name = "tb_user_course")
public class UserCourse extends BaseEntity
{
    private static final long serialVersionUID = 6336381110924981182L;

    @Id
    private Integer id;

    private Integer user_id;

    private Integer course_id;

    private Integer class_id;

    private Integer select_type;

    /**
     * 选课类型 自主选学
     */
    public static final Integer COURSE_SELECT_TYPE_SELF = 0;

    /**
     * 选课类型 班级选课
     */
    public static final Integer COURSE_SELECT_TYPE_CLASS = 1;

    private String select_date;

    private BigDecimal period;

    private BigDecimal credit;

    private Integer course_type;

    /**
     * 课程类型 必修课
     */
    public static final Integer COURSE_TYPE_REQUIRED = 0;

    /**
     * 课程类型 选修课
     */
    public static final Integer COURSE_TYPE_ELECTIVE = 1;

    private String complete_date;

    private Integer complete_year;

    private Integer status;

    /**
     * 学习状态 正在学习
     */
    public static final Integer STATUS_ONGOING = 0;

    /**
     * 学习状态 已经完成
     */
    public static final Integer STATUS_COMPLETE = 1;

    private Integer test_score;

    private Integer study_times;

    private Integer study_duration;

    private String last_progress;

    private Integer test_pass;

    private String ipad_progress;

    private String last_study_time;

    private String serialize_sco;
    
    private Integer assign_id;

    public String getSerialize_sco()
    {
        return serialize_sco;
    }

    public void setSerialize_sco(String serializeSco)
    {
        serialize_sco = serializeSco;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        return this.id.equals(((UserCourse) obj).getId());
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer userId)
    {
        user_id = userId;
    }

    public Integer getCourse_id()
    {
        return course_id;
    }

    public void setCourse_id(Integer courseId)
    {
        course_id = courseId;
    }

    public Integer getClass_id()
    {
        return class_id;
    }

    public void setClass_id(Integer classId)
    {
        class_id = classId;
    }

    public Integer getSelect_type()
    {
        return select_type;
    }

    public void setSelect_type(Integer selectType)
    {
        select_type = selectType;
    }

    public String getSelect_date()
    {
        return select_date;
    }

    public void setSelect_date(String selectDate)
    {
        select_date = selectDate;
    }

    public BigDecimal getPeriod()
    {
        return period;
    }

    public void setPeriod(BigDecimal period)
    {
        this.period = period;
    }

    public BigDecimal getCredit()
    {
        return credit;
    }

    public void setCredit(BigDecimal credit)
    {
        this.credit = credit;
    }

    public Integer getCourse_type()
    {
        return course_type;
    }

    public void setCourse_type(Integer courseType)
    {
        course_type = courseType;
    }

    public String getComplete_date()
    {
        return complete_date;
    }

    public void setComplete_date(String completeDate)
    {
        complete_date = completeDate;
    }

    public Integer getComplete_year()
    {
        return complete_year;
    }

    public void setComplete_year(Integer completeYear)
    {
        complete_year = completeYear;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getTest_score()
    {
        return test_score;
    }

    public void setTest_score(Integer testScore)
    {
        test_score = testScore;
    }

    public Integer getStudy_times()
    {
        return study_times;
    }

    public void setStudy_times(Integer studyTimes)
    {
        study_times = studyTimes;
    }

    public Integer getStudy_duration()
    {
        return study_duration;
    }

    public void setStudy_duration(Integer studyDuration)
    {
        study_duration = studyDuration;
    }

    public String getLast_progress()
    {
        return last_progress;
    }

    public void setLast_progress(String lastProgress)
    {
        last_progress = lastProgress;
    }

    public Integer getTest_pass()
    {
        return test_pass;
    }

    public void setTest_pass(Integer testPass)
    {
        test_pass = testPass;
    }

    public String getIpad_progress()
    {
        return ipad_progress;
    }

    public void setIpad_progress(String ipadProgress)
    {
        ipad_progress = ipadProgress;
    }

    public String getLast_study_time()
    {
        return last_study_time;
    }

    public void setLast_study_time(String lastStudyTime)
    {
        last_study_time = lastStudyTime;
    }

    public Integer getAssign_id()
    {
        return assign_id;
    }

    public void setAssign_id(Integer assignId)
    {
        assign_id = assignId;
    }

}
