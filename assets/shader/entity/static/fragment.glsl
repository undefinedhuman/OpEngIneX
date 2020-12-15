#version 140

const int MAX_POINT_LIGHTS = 5;

in vec4 positionInWorldSpace;
in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 toCameraRay;
in float fogFactor;

out vec4 fragColor;

uniform sampler2D textureSampler;

struct DirectionalLight {
    float intensity;
    vec3 direction;
    vec3 color;
};

uniform DirectionalLight sun;

struct PointLight {
    float intensity;
    vec3 position;
    vec3 color;
    vec3 attenuation;
};

uniform PointLight pointLights[MAX_POINT_LIGHTS];

uniform vec3 fogColor;

uniform float ambientValue;
uniform float specularStrength;
uniform float shineDamper;

vec4 calcLightColor(vec4 textureColor, float lightIntensity, vec3 lightColor, vec3 unitLight, vec3 unitCamera, vec3 unitNormal) {
    // Diffuse
    float brightness = max(dot(unitNormal, unitLight), 0.0);
    vec4 diffuse = textureColor * brightness * vec4(lightColor, 1) * lightIntensity;

    // Specular
    vec3 halfwayDir = normalize(unitLight + unitCamera);
    float specularFactor = pow(max(dot(unitNormal, halfwayDir), 0.0), shineDamper);
    vec4 specular = specularStrength * lightIntensity * specularFactor * vec4(lightColor, 1);

    return (diffuse + specular);
}

vec4 calcPointLight(vec4 textureColor, PointLight light, vec3 unitCamera, vec3 unitNormal) {
    vec3 lightDirection = light.position - positionInWorldSpace.xyz;
    vec4 ligthColor = calcLightColor(textureColor, light.intensity, light.color, normalize(lightDirection), unitCamera, unitNormal);

    float distance = length(lightDirection);
    float attentuation = light.attenuation.x + light.attenuation.y * distance + light.attenuation.z * distance * distance;
    return ligthColor / attentuation;
}

vec4 calcDirectionalLight(vec4 textureColor, DirectionalLight light, vec3 unitCamera, vec3 unitNormal) {
    return calcLightColor(textureColor, light.intensity, light.color, normalize(light.direction), unitCamera, unitNormal);
}

void main() {
    vec4 textureColor = texture(textureSampler, passTextureCoords);
    if(textureColor.a < 0.5) discard;

    vec3 unitCamera = normalize(toCameraRay);
    vec3 unitNormal = normalize(surfaceNormal);

    vec4 diffuseSpecular = calcDirectionalLight(textureColor, sun, unitCamera, unitNormal);

    for(int i = 0; i < MAX_POINT_LIGHTS; i++) {
        if(pointLights[i].intensity > 0) {
            diffuseSpecular += calcPointLight(textureColor, pointLights[i], unitCamera, unitNormal);
        }
    }

    fragColor = vec4(vec3(ambientValue), 1) * textureColor + diffuseSpecular;
    fragColor = mix(vec4(fogColor, 1), fragColor, fogFactor);
}
