-- year
    update topic set year=coalesce(extract(year from t0), extract(year from creation));
    update reply set year=coalesce(extract(year from t0), extract(year from creation));
    update acdoc set year=coalesce(extract(year from t0), extract(year from creation));
    update sdoc  set year=coalesce(extract(year from t0), extract(year from creation));
    update stdoc set year=coalesce(extract(year from t0), extract(year from creation));
    update dldoc set year=coalesce(extract(year from t0), extract(year from creation));

